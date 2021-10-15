package simplilearn.sportyshoes.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	

	@Id
	@GeneratedValue
	private Integer id;
	
	private String title;
	
	private float price;
	
	@ManyToOne
	private Category category;
	
	@OneToMany(mappedBy = "product")
	private List<OrderItem> orderItem = new ArrayList<OrderItem>();

	public Product(String title, float price) {
		super();
		this.title = title;
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", title=" + title + ", price=" + price + "]";
	}
	
	
}
