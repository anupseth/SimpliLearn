package simplilearn.sportyshoes.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import simplilearn.sportyshoes.entities.User;
import simplilearn.sportyshoes.service.CommonServiceClass;

@Controller
public class AdminController {

	@Autowired
	private CommonServiceClass service;

	@GetMapping("/admin")
	public String getAdminPage(HttpSession session) {
		session.setAttribute("adminErr", "");
		
		if (session.getAttribute("adminUser") == null) {
			session.setAttribute("singinError",
					"Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}
		
		session.setAttribute("singinError", "");
		
		return "Admin";
	}

	@GetMapping("/ChangePasword")
	public String changePassword(User user, HttpSession session) {
		
		
		if (session.getAttribute("adminUser") == null) {
			session.setAttribute("singinError",
					"Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}
		
		return "PasswordChange";
	}

	@PostMapping("/ChangePasword")
	public String changePasswordPost(@Valid User user, BindingResult bindingResult, HttpSession session) {

		if(bindingResult.hasErrors()) {
			return "PasswordChange";
		}
		
		
		boolean success = service.changePasswordForAdmin(user, session);

		if (success) {
			
			return "redirect:/logout";
			
		} else {
			return "redirect:/ChangePasword";
		}

	}

}
