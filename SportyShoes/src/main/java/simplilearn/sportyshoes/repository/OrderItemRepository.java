package simplilearn.sportyshoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import simplilearn.sportyshoes.entities.Category;
import simplilearn.sportyshoes.entities.OrderItem;


public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
	
	
}
