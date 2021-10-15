package simplilearn.sportyshoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import simplilearn.sportyshoes.entities.Category;
import simplilearn.sportyshoes.entities.Order;


public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	
}
