package simplilearn.sportyshoes.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import simplilearn.sportyshoes.entities.Order;
import simplilearn.sportyshoes.entities.Status;


public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	public Order findByStatus(Status status);
	
	@Query("Select o from Order o where o.orderDate between ?1 and ?2")
	public List<Order> getByDates(LocalDate localDate, LocalDate localDate2);
}
