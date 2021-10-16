package simplilearn.sportyshoes.controller;

import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

		service.saveUser(user);

		User user1 = new User();
		user1.setUsername("aaaaa");
		user1.setPassword(PasswordEncoderDecoderUtil.encodePassword("aaaaa"));
		service.saveUser(user1);

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
		// encode Password
		encodedPassword = PasswordEncoderDecoderUtil.encodePassword(user.getPassword());

		if (!encodedPassword.isEmpty()) {
			user.setPassword(encodedPassword);
		}

		if (!user.getUsername().equalsIgnoreCase("admin")) {

			boolean singUpUser = userService.singUpUser(user);

			if (!singUpUser) {

				session.setAttribute("singupError", "Username Already exist.... Please singup with different username");
				// return "redirect:/singup";
				return "Singup";
			}

			session.setAttribute("singupError", "");
			return "redirect:/";

		} else {

			session.setAttribute("singupError", "Username Already exist.... Please singup with different username");
			// return "redirect:/singup";
			return "Singup";

		}

	}

	@PostMapping("/singin")
	public String singinPost(@Valid User user, BindingResult bindingResult, HttpSession session) {

		if (bindingResult.hasErrors()) {
			return "Home";
		}

		boolean singInUser = userService.singInUser(user);

		if (!singInUser) {

			session.setAttribute("singinError",
					"Either user is not registered with us or Credentials provided is not correct");
			// return "redirect:/";
			return "Home";
		}

		session.setAttribute("currentUser", user.getUsername());
		session.setAttribute("singinError", "");
		return "redirect:/products";
	}

	@GetMapping("/products")
	public String productsPage(HttpSession session, Model model) {

		if (session.getAttribute("currentUser") == null) {
			session.setAttribute("singinError",
					"Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}
		List<Product> allProducts = service.getAllProducts();

		model.addAttribute("dtoList", allProducts);

		return "Products";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {

		session.setAttribute("currentUser", null);
		session.setAttribute("orderCart", null);
		return "redirect:/";

	}

	@GetMapping("/orderProduct/{id}")
	public String deleteCourse(@PathVariable Integer id, Model model, HttpSession session) {

		if (session.getAttribute("currentUser") == null) {
			session.setAttribute("singinError",
					"Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}

		// System.out.println(" ----------id = "+id);

		Product prod = service.getProductById(id);
		// System.out.println(" ---------- prod ="+ prod);

		ProductOrderDTO prodDto = new ProductOrderDTO();
		prodDto.setProdId(prod.getId());
		prodDto.setProdPrice(prod.getPrice());
		prodDto.setProdTitle(prod.getTitle());
		prodDto.setQuantity(1);

		model.addAttribute("productOrderDTO", prodDto);

		return "Order";
	}

	@PostMapping("/addProductToCart")
	public String addOrdertoOrderItem(@Valid ProductOrderDTO prodOrDTO, BindingResult bindingResult,
			HttpSession session) {
		if (session.getAttribute("currentUser") == null) {
			session.setAttribute("singinError",
					"Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}

		if (bindingResult.hasErrors())
			return "Order";
		else {

			Order order = service.addOrderItemToCart(prodOrDTO);

			session.setAttribute("orderCart", order);

			return "redirect:/products";
		}

	}
	
	
	@PostMapping("/executeOrder")
	public String executeOrder(HttpSession session) {
		System.out.println(" ***************** inside post *****************");
		
		Order order = (Order) session.getAttribute("orderCart");
		boolean orderExecuted = false;
		if(order != null) {
			orderExecuted = service.executeOrder(order.getId());
		}
		
		if(orderExecuted) {
			session.setAttribute("orderCart", null);
			session.setAttribute("finalOrder", service.getOrder(order.getId()));
		}
		
		return "OrderExcuted";
	}

	@ExceptionHandler(Exception.class)
	public String handleExcep(HttpServletRequest req, Exception ex,Model model) {
		System.out.println("Request: " + req.getRequestURL() + " raised " + ex);
		model.addAttribute("exception",ex.getMessage());
		model.addAttribute("url",req.getRequestURL());
		return "Error";
	}

}
