package simplilearn.sportyshoes.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductOrderDTO {
	
	private Integer prodId;
	
	private Float prodPrice;
	
	private String prodTitle;
	
	@Min(value = 1, message = "Minimun quantity should be 1")
	@Max(value = 10, message = "Cannot order more then 10 quantity in single order")
	private Integer quantity;

}
