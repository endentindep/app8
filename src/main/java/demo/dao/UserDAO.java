package demo.dao;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import demo.models.Role;
import demo.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Component
public class UserDAO {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void saveUser(User user) {
		em.persist(user);
	}

	@Transactional
	public User findUser(Long id) {
		return em.find(User.class, id);
	}

	@Transactional
	public void removeUser(Long id) {
		User user = em.find(User.class, id);
		em.remove(user);
	}

	@Transactional
	public void updateUser(Long id, String name, String password, HashSet<Role> roles) {
		User user = em.find(User.class, id);
		Set<Role> set = user.getRoles();
		Iterator<Role> iterRemove = set.iterator();
		Iterator<Role> iterSetter = roles.iterator();
		while (iterRemove.hasNext()) {
			em.remove(em.find(Role.class, iterRemove.next().getId()));
		}
		while (iterSetter.hasNext()) {
			iterSetter.next().setUser(user);
		}
		user.setName(name);
		user.setPassword(password);
		user.setRoles(roles);
		em.merge(user);
	}

	@Transactional
	public List<? extends User> getUsers() {
		return em.createQuery("from User").getResultList();
	}

	@Transactional
	public List<Role> getRoles(Long id) {
		List<Role> roles = em.createQuery("select roles from User user where user.id = " + id).getResultList();
		return roles;
	}
}
