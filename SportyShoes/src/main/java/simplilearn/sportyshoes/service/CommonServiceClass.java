package simplilearn.sportyshoes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import simplilearn.sportyshoes.entities.Order;
import simplilearn.sportyshoes.entities.OrderItem;
import simplilearn.sportyshoes.entities.Product;
import simplilearn.sportyshoes.entities.Status;
import simplilearn.sportyshoes.entities.User;
import simplilearn.sportyshoes.repository.CategoryRepository;
import simplilearn.sportyshoes.repository.OrderItemRepository;
import simplilearn.sportyshoes.repository.OrderRepository;
import simplilearn.sportyshoes.repository.ProductRepository;
import simplilearn.sportyshoes.repository.UserRepository;

@Service
@Transactional
public class CommonServiceClass {


	@Autowired
	private  CategoryRepository catRepo;
	
	@Autowired
	private  ProductRepository proRepo;
	
	@Autowired
	private  OrderRepository ordRepo;
	
	@Autowired
	private  OrderItemRepository ordItemRepo;
	
	@Autowired
	private  UserRepository userRepo;
	
	
	public List<Product> getAllProducts(){
		return proRepo.findAll();
	}
	
	
//	public void createUser(User user) {
//		userRepo.save(user);
//	}


	public Product getProductById(Integer id) {
		Optional<Product> findById = proRepo.findById(id);
		if(findById.isPresent())
			return findById.get();
		else 
			return null;
	}


	public Order findOrderByStatus(Status status) {
		Order findByStatus = ordRepo.findByStatus(status);
		return findByStatus;
	}


	public OrderItem findOrderItemByProductAndStatus(Product productById, Status status) {
		OrderItem findByProductAndStatus = ordItemRepo.findByProductAndStatus(productById, status);
		return findByProductAndStatus;
	}
	
	public void saveEntities(Object ...entities) {

		if(entities.length >= 1) {
			for (int i = 0; i < entities.length; i++) {
				Object object = entities[i];
				
				if(object instanceof Order) {
					ordRepo.save(((Order)object));
				}else if(object instanceof OrderItem) {
					ordItemRepo.save(((OrderItem)object));
				}else if(object instanceof User) {
					userRepo.save(((User)object));
				}
			}
		}
	}
}
