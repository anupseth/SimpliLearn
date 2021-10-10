package simplilearn.sportyshoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import simplilearn.sportyshoes.entities.User;


public interface UserRepository extends JpaRepository<User, Integer> {
	
	public User findByUsername(String user);
	
}
