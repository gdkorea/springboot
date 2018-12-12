package net.slipp.web;
import java.util.ArrayList;
import java.util.List;

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
	public String updateform(@PathVariable Long id, Model model)
	{
		User user =  userRepository.findById(id).get();
		model.addAttribute("user", user);
		return "/user/updateform";
	}
	
	@PutMapping("/{id}")
	public String Update(@PathVariable Long id, User newUser)
	{
		User user =  userRepository.findById(id).get();
		user.update(newUser);
		userRepository.save(user);
		return "redirect:/users";
	}
	
	
	@GetMapping("/login")
	public String login()
	{
		return "/user/login";
	}

}
