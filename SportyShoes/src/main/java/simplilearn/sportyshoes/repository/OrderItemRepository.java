package simplilearn.sportyshoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import simplilearn.sportyshoes.entities.OrderItem;
import simplilearn.sportyshoes.entities.Product;
import simplilearn.sportyshoes.entities.Status;


public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
	
	public OrderItem findByProductAndStatus(Product product,Status status);
	
}
