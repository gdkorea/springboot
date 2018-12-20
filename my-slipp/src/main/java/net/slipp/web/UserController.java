package net.slipp.web;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.User;
import net.slipp.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	private List<User> users = new ArrayList<User>();
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/loginForm")
	public String loginform()
	{
		return "/user/login";
	}
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session)
	{
		System.out.println("userId : " + userId + ", password : " + password);
		User user =  userRepository.findByUserId(userId);
		if(user == null)
		{
			System.out.println("Login Failure1");
			return "redirect:/users/loginForm";
		}
		
		
		if(!user.matchPassword(password))
		{
			System.out.println("Login Failure2");
			return "redirect:/users/loginForm";
		}
		
		System.out.println("Login Success");
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
		
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session)
	{
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		return "redirect:/";
	}
	
	
	@PostMapping("")
	public String Create(User user)
	{
		System.out.println("userId : " + user.getUserId() + ", email : " + user.getEmail() + ", name : " + user.getName());
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("")
	public String List(Model model)
	{
		model.addAttribute("users",userRepository.findAll());
		return "/user/list";
	}
	
	@GetMapping("/form")
	public String form()
	{
		return "/user/form";
	}
	
	
	@GetMapping("/{id}/form")
	public String updateform(@PathVariable Long id, Model model,HttpSession session)
	{
		
		if(!HttpSessionUtils.isLoginUser(session))
		{
			return "redirect:/users/login";
		}
		
		User sessionedUser = (User)HttpSessionUtils.getUserFromSession(session);
	
		if(!sessionedUser.matchId(id)) {
			throw new IllegalStateException("you can modify yours");
		}

		
		User user =  userRepository.findById(id).get();
		model.addAttribute("user", user);
		return "/user/updateform";
	}
	
	@PutMapping("/{id}")
	public String Update(@PathVariable Long id, User updatedUser, HttpSession session)
	{
		
		if(!HttpSessionUtils.isLoginUser(session))
		{
			return "redirect:/users/login";
		}
		
		User sessionedUser = (User)HttpSessionUtils.getUserFromSession(session);
		
		if(!sessionedUser.matchId(id))
		{
			throw new IllegalStateException("you can modify yours");
		}

		User user =  userRepository.findById(id).get();
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users";
	}
	
	
	@GetMapping("/login")
	public String login()
	{
		return "/user/login";
	}

}
