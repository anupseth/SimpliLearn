package simplilearn.sportyshoes.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import simplilearn.sportyshoes.dto.ProductOrderDTO;
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
	private CategoryRepository catRepo;

	@Autowired
	private ProductRepository proRepo;

	@Autowired
	private OrderRepository ordRepo;

	@Autowired
	private OrderItemRepository ordItemRepo;

	@Autowired
	private UserRepository userRepo;

	public List<Product> getAllProducts() {
		return proRepo.findAll();
	}

//	public void createUser(User user) {
//		userRepo.save(user);
//	}

	public Product getProductById(Integer id) {
		Optional<Product> findById = proRepo.findById(id);
		if (findById.isPresent())
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

//	public void saveEntities(Object... entities) {
//
//		if (entities.length >= 1) {
//			for (int i = 0; i < entities.length; i++) {
//				Object object = entities[i];
//
//				if (object instanceof Order) {
//					Order save = ordRepo.save(((Order) object));
//					System.out.println(" -------------------- Order Save : " + save);
//				} else if (object instanceof OrderItem) {
//					OrderItem save = ordItemRepo.save(((OrderItem) object));
//					System.out.println(" --------------------- OrderItem Save : " + save);
//				} else if (object instanceof User) {
//					userRepo.save(((User) object));
//				}
//			}
//		}
//	}
	
	public User saveUser(User user) {
		return userRepo.save(user);
	}
	
	public Order saveOrder(Order order) {
		return ordRepo.save(order);
	}
	
	public OrderItem saveOrderItem(OrderItem orderItem) {
		return ordItemRepo.save(orderItem);
	}

	@Transactional
	public Order addOrderItemToCart(ProductOrderDTO prodOrDTO) {

		Product productById = getProductById(prodOrDTO.getProdId());
		OrderItem orderItem = null;

		Order order = findOrderByStatus(Status.INPROGRESS);

		if (order == null) {
			order = new Order();
			order.setOrderDate(LocalDate.now());
			order.setStatus(Status.INPROGRESS);
			order.setOrderNumber("ORD" + LocalDateTime.now().getDayOfMonth() + LocalDateTime.now().getMonthValue()
					+ LocalDateTime.now().getMinute() + LocalDateTime.now().getSecond());
		}

		List<OrderItem> collect = order.getOrderItem().stream()
				.filter(ordItem -> ordItem.getProduct().getId() == productById.getId()).collect(Collectors.toList());

		if (collect.size() >= 1) {

			for (OrderItem ordItem : collect) {

				if (ordItem.getProduct().getId() == productById.getId()) {
					ordItem.setQuantity(ordItem.getQuantity() + prodOrDTO.getQuantity());
					ordItem.setOrderItemTotal(ordItem.getQuantity() * ordItem.getPrice());
					order.setOrderTotal(order.getOrderTotal() + ordItem.getOrderItemTotal());
				}

			}

		} else {
			orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setPrice(prodOrDTO.getProdPrice());
			orderItem.setProduct(productById);
			orderItem.setQuantity(prodOrDTO.getQuantity());
			orderItem.setOrderItemTotal(orderItem.getQuantity() * orderItem.getPrice());
			order.setOrderTotal(order.getOrderTotal() + orderItem.getOrderItemTotal());
			orderItem.setStatus(Status.INPROGRESS);
			//order.getOrderItem().add(orderItem);
		}
		// OrderItem orderItem =
		// findOrderItemByProductAndStatus(productById,Status.INPROGRESS);
		/*
		 * if(orderItem == null) { orderItem = new OrderItem();
		 * orderItem.setOrder(order); orderItem.setPrice(prodOrDTO.getProdPrice());
		 * orderItem.setProduct(productById);
		 * orderItem.setQuantity(prodOrDTO.getQuantity());
		 * orderItem.setOrderItemTotal(orderItem.getQuantity() * orderItem.getPrice());
		 * order.setOrderTotal(order.getOrderTotal() + orderItem.getOrderItemTotal());
		 * orderItem.setStatus(Status.INPROGRESS); }else {
		 * orderItem.setQuantity(prodOrDTO.getQuantity());
		 * orderItem.setOrderItemTotal(orderItem.getQuantity() * orderItem.getPrice());
		 * order.setOrderTotal(order.getOrderTotal() + orderItem.getOrderItemTotal()); }
		 */

//		Order saveOrder = ordRepo.save(order);
//		OrderItem saveOrderItem = ordItemRepo.save(orderItem);
//		
//		if(saveOrder.getOrderItem().size()-1 >= 0)
//			saveOrder.getOrderItem().set(saveOrder.getOrderItem().size()-1, saveOrderItem);
		
		Order saveOrder = ordRepo.save(order);
		OrderItem saveOrderItem = ordItemRepo.save(orderItem);
		
		saveOrder.getOrderItem().add(saveOrderItem);

		return saveOrder;
	}
}
