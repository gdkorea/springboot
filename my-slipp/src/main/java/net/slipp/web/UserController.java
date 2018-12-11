package net.slipp.web;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.User;
import net.slipp.domain.UserRepository;

@Controller
public class UserController {
	private List<User> users = new ArrayList<User>();
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/create")
	public String Create(User user)
	{
		System.out.println("userId : " + user.getUserId() + ", email : " + user.getEmail() + ", name : " + user.getName());
		userRepository.save(user);
		return "redirect:/list";
	}
	
	@GetMapping("/list")
	public String List(Model model)
	{
		model.addAttribute("users",userRepository.findAll());
		return "list";
	}

}
