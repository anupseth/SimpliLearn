package simplilearn.sportyshoes.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	private Product product;
	
	private Integer quantity;
	
	@ManyToOne
	private Order order;

	public OrderItem(Integer quantity) {
		super();
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "OrderItem [product_Title=" + product.getTitle() + ", quantity= " + quantity + " product_Category= "  + product.getCategory().getName() + "]";
	}
	
	

}
