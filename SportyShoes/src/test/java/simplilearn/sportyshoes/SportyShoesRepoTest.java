package simplilearn.sportyshoes;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import simplilearn.sportyshoes.entities.Category;
import simplilearn.sportyshoes.entities.Order;
import simplilearn.sportyshoes.entities.OrderItem;
import simplilearn.sportyshoes.entities.Product;
import simplilearn.sportyshoes.repository.CategoryRepository;
import simplilearn.sportyshoes.repository.OrderItemRepository;
import simplilearn.sportyshoes.repository.OrderRepository;
import simplilearn.sportyshoes.repository.ProductRepository;

@SpringBootTest
class SportyShoesRepoTest {
	
	@Autowired
	private  CategoryRepository catRepo;
	
	@Autowired
	private  ProductRepository proRepo;
	
	@Autowired
	private  OrderRepository ordRepo;
	
	@Autowired
	private  OrderItemRepository ordItemRepo;
	
	@Test
	@Transactional
	public void testProductCategoryRepo() {
		
		try {
			
		
		
		Product pro1 = new Product("proA1", 43f);
		
		Product pro2 = new Product("proA2", 63f);
		
		Product pro3 = new Product("proB", 83f);
		
		Category cat1 = new Category("catA");
		Category cat2 = new Category("catB");
		
		cat1.getProducts().add(pro1);
		cat1.getProducts().add(pro2);
		cat2.getProducts().add(pro3);
		
		pro1.setCategory(cat1);
		pro2.setCategory(cat1);
		pro3.setCategory(cat2);
		
		OrderItem oI1 = new OrderItem(5);
		oI1.setProduct(pro1);
		
		OrderItem oI2 = new OrderItem(7);
		oI2.setProduct(pro2);
		
		OrderItem oI3 = new OrderItem(12);
		oI3.setProduct(pro3);
		
		pro1.getOrderItem().add(oI1);
		pro2.getOrderItem().add(oI2);
		pro3.getOrderItem().add(oI3);
		
		
		Order order = new Order();
		order.setOrderDate(LocalDate.now());
		order.getOrderItem().add(oI3);
		order.getOrderItem().add(oI2);
		order.getOrderItem().add(oI1);
		
		
		oI1.setOrder(order);
		oI2.setOrder(order);
		oI3.setOrder(order);
		
		catRepo.save(cat1);
		catRepo.save(cat2);
		
		proRepo.save(pro1);
		proRepo.save(pro2);
		proRepo.save(pro3);
		
		ordRepo.save(order);
	
		ordItemRepo.save(oI1);
		ordItemRepo.save(oI2);
		ordItemRepo.save(oI3);
		
		
		
		List<Product> findAllProd = proRepo.findAll();
		
		//findAllProd.forEach(prod -> System.out.println("********************" +prod.toString()+  "  "+prod.getCategory() ));
		
		Assertions.assertEquals(findAllProd.size(), 3);
		
		List<Order> findAllOrder = ordRepo.findAll();
		
		findAllOrder.forEach((ord) -> {
			System.out.println("====  order date  : " + ord.getOrderDate() );
			
			System.out.println("====  order Item  : " + ord.getOrderItem());
		});
		
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

}
