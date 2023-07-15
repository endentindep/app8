package demo.userservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import demo.models.User;

@Component
public interface UserRepository extends JpaRepository<User, Long> {
	User findByName(String name);
}