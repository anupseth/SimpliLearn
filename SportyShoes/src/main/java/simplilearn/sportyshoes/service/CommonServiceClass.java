package simplilearn.sportyshoes.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import simplilearn.sportyshoes.dto.OrderReportCatDTO;
import simplilearn.sportyshoes.dto.ProductOrderDTO;
import simplilearn.sportyshoes.entities.Category;
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
import simplilearn.sportyshoes.util.PasswordEncoderDecoderUtil;

@Service
@Transactional
public class CommonServiceClass {

	@Autowired
	private CategoryRepository catRepo;

	@Autowired
	private ProductRepository prodRepo;

	@Autowired
	private OrderRepository ordRepo;

	@Autowired
	private OrderItemRepository ordItemRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private EntityManager em;

	public List<Product> getAllProducts() {
		return prodRepo.findAll();
	}

//	public void createUser(User user) {
//		userRepo.save(user);
//	}

	public Product getProductById(Integer id) {
		Optional<Product> findById = prodRepo.findById(id);
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
					order.getOrderItem().remove(ordItem);
					orderItem = ordItemRepo.findById(ordItem.getId()).get();
					orderItem.setQuantity(ordItem.getQuantity() + prodOrDTO.getQuantity());
					orderItem.setOrderItemTotal(ordItem.getQuantity() * ordItem.getPrice());
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
			// order.getOrderItem().add(orderItem);
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

		// Order saveOrder = ordRepo.save(order);
		OrderItem saveOrderItem = ordItemRepo.save(orderItem);

		order.getOrderItem().add(saveOrderItem);

		return ordRepo.save(order);
	}

	public boolean executeOrder(Integer id) {
		Optional<Order> findById = ordRepo.findById(id);
		Order order = null;
		if (findById.isPresent()) {
			order = findById.get();
			order.setStatus(Status.COMPLETED);
			order.getOrderItem().forEach(ordItem -> ordItem.setStatus(Status.COMPLETED));
			return true;
		}

		return false;
	}

	public Order getOrder(Integer integer) {
		Optional<Order> findById = ordRepo.findById(integer);

		if (findById.isPresent())
			return findById.get();

		return null;
	}

	public boolean changePasswordForAdmin(@Valid User user, HttpSession session) {
		User userFromDb = userRepo.findByUsername("admin");

		String decodePassword = PasswordEncoderDecoderUtil.decodePassword(userFromDb.getPassword());

		if (user.getPassword().equalsIgnoreCase(decodePassword)) {
			session.setAttribute("adminErr", "Password must be different from previous password");
			return false;
		}
		userFromDb.setPassword(PasswordEncoderDecoderUtil.encodePassword(user.getPassword()));
		userRepo.save(userFromDb);

		session.setAttribute("adminChPaSu", "Password Changed Successfully. Please login again");
		return true;
	}

	public List<User> getAllUsers() {

		return userRepo.findAll();
	}

	public User findUserByUserName(String username) {
		User findByUsername = userRepo.findByUsername(username);
		return findByUsername;
	}

	public List<String> getAllCategory() {
		return catRepo.getCatNames();
	}

	public void UpdateProduct(Product product) {

		Category category = catRepo.findByName(product.getCat());

		if (product.getId() == null) {
			product.setCategory(category);
			product.setTitle(product.getTitle());
			product.setPrice(product.getPrice());

			category.getProducts().add(product);

			prodRepo.save(product);
			catRepo.save(category);
		} else {

			Optional<Product> optionalProduct = prodRepo.findById(product.getId());

			if (optionalProduct.isPresent()) {

				Product productFrmDb = optionalProduct.get();

				category.getProducts().remove(productFrmDb);

				productFrmDb.setCategory(category);
				productFrmDb.setTitle(product.getTitle());
				productFrmDb.setPrice(product.getPrice());

				category.getProducts().add(productFrmDb);

				prodRepo.save(productFrmDb);
				catRepo.save(category);

			}

		}

	}

	public void deleteProduct(Integer id) {

		Optional<Product> findById = prodRepo.findById(id);

		if (findById.isPresent()) {
			Product product = findById.get();
			prodRepo.delete(product);
		}

	}

	public List<Order> getOrderByDate(String fromDate, String toDate) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		// convert String to LocalDate
		LocalDate localDate = LocalDate.parse(fromDate, formatter);

		LocalDate localDate2 = LocalDate.parse(toDate, formatter);
		List<Order> byDates = ordRepo.getByDates(localDate, localDate2);

		System.out.println("****************************************");
		System.out.println(byDates);

		//testJoinQuery();

		return byDates;
	}

	public List<OrderReportCatDTO> getReportByCategory(String catName) {

		String query = "select po.ORDER_NUMBER, p.TITLE, p.PRICE, o.QUANTITY, o.ORDER_ITEM_TOTAL, c.NAME from ORDER_ITEM o INNER JOIN PROD_ORDER po ON o.ORDER_ID = po.ID INNER JOIN PRODUCT p  ON o.PRODUCT_ID = p.ID INNER JOIN CATEGORY c ON p.CATEGORY_ID = c.ID where c.NAME = :Cate";
		Query queryNative = em.createNativeQuery(query);
		queryNative.setParameter("Cate", catName);
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = queryNative.getResultList();
		System.out.println("----------- NAtive query Alert ------------ ");
		System.out.println(resultList);
		List<OrderReportCatDTO> orderRepoList = new ArrayList<OrderReportCatDTO>();

		resultList.forEach((obj) -> {
			OrderReportCatDTO  dto = new OrderReportCatDTO();
			dto.setOrderNumber((String)obj[0]);
			dto.setTitle((String)obj[1]);
			dto.setPrice((Double)obj[2]);
			dto.setQuantity((int)obj[3]);
			dto.setOrderItemTotal((Double)obj[4]);
			orderRepoList.add(dto);
		});
		 
		System.out.println(orderRepoList);
		return orderRepoList;
	}
}
