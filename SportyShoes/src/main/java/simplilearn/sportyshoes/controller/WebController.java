package simplilearn.sportyshoes.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import simplilearn.sportyshoes.entities.User;
import simplilearn.sportyshoes.service.UserService;
import simplilearn.sportyshoes.util.PasswordEncoderDecoderUtil;

@Controller
public class WebController {
	
	@Autowired
	private UserService userService;
	
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
		
		if (bindingResult.hasErrors()) {
			return "Singup";
		}
		//encode Password
		encodedPassword = PasswordEncoderDecoderUtil.encodePassword(user.getPassword());
		
		
		if(!encodedPassword.isEmpty()) {
			user.setPassword(encodedPassword);
		}
		
		boolean singUpUser = userService.singUpUser(user);
		
		if(!singUpUser) {
			
			session.setAttribute("singupError", "Username Already exist.... Please singup with different username");
			//return "redirect:/singup";
			return "Singup";
		}

		session.setAttribute("singupError", "");
		return "redirect:/";
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
	public String productsPage(HttpSession session) {
		
		if(session.getAttribute("currentUser") == null)
		{
			session.setAttribute("singinError", "Either user is not registered with us or Credentials provided is not correct");
			return "redirect:/";
		}
			
			
		return "Products";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.setAttribute("currentUser",null);
		return "redirect:/";
		
	}

}
