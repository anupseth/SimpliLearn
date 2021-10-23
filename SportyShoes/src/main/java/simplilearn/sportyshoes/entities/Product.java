package simplilearn.sportyshoes.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
@SQLDelete(sql = "UPDATE product SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Product {
	

	@Id
	@GeneratedValue
	private Integer id;
	
	@NotBlank
	private String title;
	
	@Min(value = 1, message = "Minimum value should be 1.0")
	private float price;
	
	@ManyToOne
	private Category category;
	
	@OneToMany(mappedBy = "product")
	private List<OrderItem> orderItem = new ArrayList<OrderItem>();
	
	@Transient
	private String cat;
	
	private boolean deleted = Boolean.FALSE;

	public Product(String title, float price) {
		super();
		this.title = title;
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", title=" + title + ", price=" + price + ", cat=" + cat + "]";
	}

	
	
}
