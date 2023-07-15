package demo.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import demo.dao.UserDAO;
import demo.models.User;

@Service
public class CustomUserDetaisService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	UserDAO dao;

	@Override
	public UserDetails loadUserByUsername(String name) {
		User user = userRepository.findByName(name);
		if (user == null) {
			throw new UsernameNotFoundException(name);
		}
		return new UserPrincipal(user, dao.getRoles((long) user.getId()));
	}

}
