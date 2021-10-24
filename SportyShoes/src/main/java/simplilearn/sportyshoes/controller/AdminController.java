package simplilearn.sportyshoes.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import simplilearn.sportyshoes.dto.DateDto;
import simplilearn.sportyshoes.dto.OrderReportCatDTO;
import simplilearn.sportyshoes.entities.Category;
import simplilearn.sportyshoes.entities.Order;
import simplilearn.sportyshoes.entities.Product;
import simplilearn.sportyshoes.entities.User;
import simplilearn.sportyshoes.service.CommonServiceClass;

@Controller
public class AdminController {

	@Autowired
	private CommonServiceClass service;

	@ExceptionHandler(Exception.class)
	public String handleExcep(HttpServletRequest req, Exception ex, Model model) {
		System.out.println("Request: " + req.getRequestURL() + " raised " + ex);
		model.addAttribute("exception", ex.getMessage());
		model.addAttribute("url", req.getRequestURL());
		return "Error";
	}

	@GetMapping("/admin")
	public String getAdminPage(HttpSession session) {
		session.setAttribute("adminErr", "");

		if (session.getAttribute("adminUser") == null) {
			session.setAttribute("singinError",
					"Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}

		session.setAttribute("singinError", "");

		return "adminPages/Admin";
	}

	@GetMapping("/ChangePasword")
	public String changePassword(User user, HttpSession session) {

		if (session.getAttribute("adminUser") == null) {
			session.setAttribute("singinError",
					"Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}

		return "adminPages/PasswordChange";
	}

	@PostMapping("/ChangePasword")
	public String changePasswordPost(@Valid User user, BindingResult bindingResult, HttpSession session) {

		if (bindingResult.hasErrors()) {
			return "adminPages/PasswordChange";
		}

		if (session.getAttribute("adminUser") == null) {
			session.setAttribute("singinError",
					"Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}

		boolean success = service.changePasswordForAdmin(user, session);

		if (success) {

			return "redirect:/logout";

		} else {
			return "redirect:/ChangePasword";
		}

	}

	@GetMapping("/users")
	public String getUsersPage(Model model, HttpSession session) {

		if (session.getAttribute("adminUser") == null) {
			session.setAttribute("singinError",
					"Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}

		model.addAttribute("userList", service.getAllUsers());

		return "adminPages/Users";
	}

	@GetMapping("/searchUser")
	public String getSearchUserPage(Model model, HttpSession session) {

		if (session.getAttribute("adminUser") == null) {
			session.setAttribute("singinError",
					"Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}

		session.setAttribute("noserach", "T");

		return "adminPages/SearchUsers";
	}

	@GetMapping("/searchUserDb")
	public String searchUser(HttpServletRequest req, HttpSession session, Model model) {

		if (session.getAttribute("adminUser") == null) {
			session.setAttribute("singinError",
					"Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}

		String username = req.getParameter("userName");

		User user = service.findUserByUserName(username);

		if (user != null)
			model.addAttribute("userName", user.getUsername());

		session.setAttribute("noserach", "F");

		return "adminPages/SearchUsers";
	}

	@GetMapping("/manageProducts")
	public String getManageproducts(Model model, HttpSession session) {

		if (session.getAttribute("adminUser") == null) {
			session.setAttribute("singinError",
					"Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}

		model.addAttribute("prodList", service.getAllProducts());

		return "adminPages/ProductsList";
	}

	@GetMapping("/manageProduct/{id}")
	public String manageProduct(@PathVariable Integer id, Model model, HttpSession session) {

		if (session.getAttribute("adminUser") == null) {
			session.setAttribute("singinError",
					"Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}

		List<String> catList = service.getAllCategory();
		model.addAttribute("catNames", catList);

		Product productById = service.getProductById(id);

		model.addAttribute("product", productById);

		return "adminPages/EditProduct";
	}

	@GetMapping("/addNewProduct")
	public String addNewProduct(Product product, HttpSession session, Model model) {

		if (session.getAttribute("adminUser") == null) {
			session.setAttribute("singinError",
					"Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}

		List<String> catList = service.getAllCategory();
		model.addAttribute("catNames", catList);

		return "adminPages/EditProduct";
	}

	@PostMapping("/editProduct")
	public String editProduct(@Valid Product product, BindingResult bindingResult, HttpSession session, Model model) {

		if (session.getAttribute("adminUser") == null) {
			session.setAttribute("singinError",
					"Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}

		if (bindingResult.hasErrors()) {
			return "adminPages/EditProduct";
		}

		service.UpdateProduct(product);

		model.addAttribute("prodList", service.getAllProducts());

		return "adminPages/ProductsList";
	}

	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable Integer id, Model model, HttpSession session) {

		if (session.getAttribute("adminUser") == null) {
			session.setAttribute("singinError",
					"Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}

		service.deleteProduct(id);

		model.addAttribute("prodList", service.getAllProducts());

		return "adminPages/ProductsList";
	}

	@GetMapping("/orderReport")
	public String showReportPage(HttpSession session) {

		if (session.getAttribute("adminUser") == null) {
			session.setAttribute("singinError",
					"Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}
		return "adminPages/ReportPage";
	}

	@GetMapping("/reportByDate")
	public String reportByDate(HttpSession session, Model model, DateDto dateDto) {

		if (session.getAttribute("adminUser") == null) {
			session.setAttribute("singinError",
					"Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}

		model.addAttribute("searchByDate", "yes");

		return "adminPages/ReportPage";
	}

	@GetMapping("/reportByCat")
	public String reportByCat(HttpSession session, Model model) {

		if (session.getAttribute("adminUser") == null) {
			session.setAttribute("singinError",
					"Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}

		model.addAttribute("categories", service.getAllCategory());
		model.addAttribute("searchByCat", "yes");
		return "adminPages/ReportPage";
	}

//	@PostMapping("/reportByDateP")
//	public String reportByDatePost(HttpServletRequest request, HttpSession session) {
//		
//		if (session.getAttribute("adminUser") == null) {
//			session.setAttribute("singinError",
//					"Either user is not registered with us or Credentials provided is not correct");
//			return "redirect:/";
//		}
//		return "adminPages/ReportPage";
//	}

	@PostMapping("/reportByDateP")
	public String reportBydate(@Valid DateDto dateDto, HttpSession session, Model model) {

		if (session.getAttribute("adminUser") == null) {
			session.setAttribute("singinError",
					"Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}

		List<Order> orderList = service.getOrderByDate(dateDto.getFromDate(), dateDto.getToDate());

		model.addAttribute("searchByDate", "yes");
		model.addAttribute("orderReport", orderList);

		return "adminPages/ReportPage";
	}
	
	
	@PostMapping("/reportByCatP")
	public String reportByCategory(HttpServletRequest request, HttpSession session, Model model) {

		if (session.getAttribute("adminUser") == null) {
			session.setAttribute("singinError",
					"Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}
		
		String categoryname = request.getParameter("categoryDrop");

		List<OrderReportCatDTO> reportByCategory = service.getReportByCategory(categoryname);

		model.addAttribute("searchByCat", "yes");
		model.addAttribute("orderReportC", reportByCategory);
		model.addAttribute("categories", service.getAllCategory());

		return "adminPages/ReportPage";
	}

}
