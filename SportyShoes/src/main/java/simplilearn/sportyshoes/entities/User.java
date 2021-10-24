package simplilearn.sportyshoes.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prod_user")
public class User {

	@Id
	@GeneratedValue
	private Integer id;
	
	@OneToMany(mappedBy = "user")
	private List<Order> orders = new ArrayList<Order>();
	
	@Size(min = 5,message = "Username should be greater then 5 charecters")
	private String username;
	
	@Size(min = 5,message = "Password should be greater then 5 Charecters")
	private String password;
	
	
}
