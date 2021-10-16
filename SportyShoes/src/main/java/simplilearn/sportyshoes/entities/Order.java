package simplilearn.sportyshoes.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Prod_Order")
public class Order {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(unique = true)
	private String orderNumber;
	
	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItem = new ArrayList<OrderItem>();
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	private LocalDate orderDate;
	
	private Float orderTotal = 0f;

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderNumber=" + orderNumber + ", orderItem=" + orderItem + ", status=" + status
				+ ", orderDate=" + orderDate + ", orderTotal=" + orderTotal + "]";
	}
	
	

}
