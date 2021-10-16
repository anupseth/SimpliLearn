package simplilearn.sportyshoes.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import simplilearn.sportyshoes.dto.ProductOrderDTO;
import simplilearn.sportyshoes.entities.Order;
import simplilearn.sportyshoes.entities.OrderItem;
import simplilearn.sportyshoes.entities.Product;
import simplilearn.sportyshoes.entities.Status;
import simplilearn.sportyshoes.entities.User;
import simplilearn.sportyshoes.service.CommonServiceClass;
import simplilearn.sportyshoes.service.UserService;
import simplilearn.sportyshoes.util.PasswordEncoderDecoderUtil;

@Controller
public class WebController {
	
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommonServiceClass service;
	
	@PostConstruct
	public void init() {
		
		User user = new User();
		user.setUsername("admin");
		user.setPassword(PasswordEncoderDecoderUtil.encodePassword("admin"));
		
		service.saveEntities(user);
		
		User user1 = new User();
		user1.setUsername("aaaaa");
		user1.setPassword(PasswordEncoderDecoderUtil.encodePassword("aaaaa"));
		service.saveEntities(user1);
		
	}
	
	@GetMapping("/")
	public String entryPoint(User user, HttpSession session) {
		session.setAttribute("singinError", "");
		session.setAttribute("singupError", "");
		return "Home";
	}

	
	
	@GetMapping("/singup")
	public String singup(User user) {
		return "Singup";
	}
	
	
	@PostMapping("/singup")
	public String singupPost(@Valid User user, BindingResult bindingResult, HttpSession session) {
		String encodedPassword = "";
		boolean save = true;
		if (bindingResult.hasErrors()) {
			return "Singup";
		}
		//encode Password
		encodedPassword = PasswordEncoderDecoderUtil.encodePassword(user.getPassword());
		
		
		if(!encodedPassword.isEmpty()) {
			user.setPassword(encodedPassword);
		}
		
		if(!user.getUsername().equalsIgnoreCase("admin")) {
			
			boolean singUpUser = userService.singUpUser(user);
			
			if(!singUpUser) {
				
				session.setAttribute("singupError", "Username Already exist.... Please singup with different username");
				//return "redirect:/singup";
				return "Singup";
			}

			session.setAttribute("singupError", "");
			return "redirect:/";
			
		}else {
			
			session.setAttribute("singupError", "Username Already exist.... Please singup with different username");
			//return "redirect:/singup";
			return "Singup";
			
		}
		
	
	}
	
	
	@PostMapping("/singin")
	public String singinPost(@Valid User user, BindingResult bindingResult, HttpSession session) {
		
		if (bindingResult.hasErrors()) {
			return "Home";
		}
		
		boolean singInUser = userService.singInUser(user);
		
		if(!singInUser) {
			
			session.setAttribute("singinError", "Either user is not registered with us or Credentials provided is not correct");
			//return "redirect:/";
			return "Home";
		}

		session.setAttribute("currentUser", user.getUsername());
		session.setAttribute("singinError", "");
		return "redirect:/products";
	}
	
	@GetMapping("/products")
	public String productsPage(HttpSession session,Model model) {
		
		if(session.getAttribute("currentUser") == null)
		{
			session.setAttribute("singinError", "Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}
		List<Product> allProducts = service.getAllProducts();
		
		model.addAttribute("dtoList", allProducts);
			
		return "Products";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.setAttribute("currentUser",null);
		session.setAttribute("orderCart", null);
		return "redirect:/";
		
	}
	
	
	@GetMapping("/orderProduct/{id}")
	public String deleteCourse(@PathVariable Integer id,Model model, HttpSession session) {
		
		if(session.getAttribute("currentUser") == null)
		{
			session.setAttribute("singinError", "Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}

		//System.out.println("  ----------id = "+id);
		
		Product prod = service.getProductById(id);
		//System.out.println(" ---------- prod  ="+ prod);
		
		ProductOrderDTO prodDto = new ProductOrderDTO();
		prodDto.setProdId(prod.getId());
		prodDto.setProdPrice(prod.getPrice());
		prodDto.setProdTitle(prod.getTitle());
		prodDto.setQuantity(1);
		
		model.addAttribute("productOrderDTO", prodDto);
		
		return "Order";
	}
	
	@PostMapping("/addProductToCart")
	public String addOrdertoOrderItem(@Valid ProductOrderDTO prodOrDTO, BindingResult bindingResult, HttpSession session) {
		
		if(session.getAttribute("currentUser") == null)
		{
			session.setAttribute("singinError", "Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}
		
		if(bindingResult.hasErrors())
			 return "Order";
		else {
			Product productById = service.getProductById(prodOrDTO.getProdId());
			
			Order order = service.findOrderByStatus(Status.INPROGRESS);
			if(order == null) {
				order = new Order();
				order.setOrderDate(LocalDate.now());
				order.setStatus(Status.INPROGRESS);
			}
			
			OrderItem orderItem = service.findOrderItemByProductAndStatus(productById,Status.INPROGRESS);
			if(orderItem == null) {
				orderItem = new OrderItem();
				orderItem.setOrder(order);
				orderItem.setPrice(prodOrDTO.getProdPrice());
				orderItem.setProduct(productById);
				orderItem.setQuantity(prodOrDTO.getQuantity());
				orderItem.setOrderItemTotal(orderItem.getQuantity() * orderItem.getPrice());
				orderItem.setStatus(Status.INPROGRESS);
			}else {
				orderItem.setQuantity(prodOrDTO.getQuantity());
				orderItem.setOrderItemTotal(orderItem.getQuantity() * orderItem.getPrice());
			}
			
			order.getOrderItem().add(orderItem);
			
			service.saveEntities(order,orderItem);
			
			session.setAttribute("orderCart", order);
			
			return "redirect:/products";
		}
		
		
	}

}
