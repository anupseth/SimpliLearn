package simplilearn.sportyshoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import simplilearn.sportyshoes.entities.Order;
import simplilearn.sportyshoes.entities.Status;


public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	public Order findByStatus(Status status);
	
	
}
