package simplilearn.sportyshoes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import simplilearn.sportyshoes.entities.Product;
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
	
	
	public void createUser(User user) {
		userRepo.save(user);
	}
}
