package simplilearn.sportyshoes.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Size(min = 5,message = "Username should be greater then 5 charecters")
	private String username;
	
	@Size(min = 5,message = "Password should be greater then 5 Charecters")
	private String password;
}
