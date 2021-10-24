package simplilearn.sportyshoes.dto;

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
public class OrderReportCatDTO {

	
	private String orderNumber;
	private String title;
	private Double price;
	private Integer quantity;
	private Double orderItemTotal = 0d;
	private String name;
	
	
}
