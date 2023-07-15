package demo.userservice;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import demo.models.Role;
import demo.models.User;

public class UserPrincipal implements UserDetails {
	private User user;

	private List<Role> roles;

	public UserPrincipal(User user, List<Role> roles) {
		this.user = user;
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		HashSet<UserAuthority> authorities = new HashSet<>();
		for (int i = 0; i < roles.size(); i++) {
			authorities.add(new UserAuthority(roles.get(i)));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String toString() {
		return user.toString();
	}
}
