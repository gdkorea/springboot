package net.slipp.web;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
	private List<User> users = new ArrayList<User>();
	
	@RequestMapping("/create")
	public String Create(User user)
	{
		System.out.println("userId : " + user.getUserId() + ", email : " + user.getEmail() + ", name : " + user.getName());
		users.add(user);
		return "redirect:/list";
	}
	
	@GetMapping("/list")
	public String List(Model model)
	{
		model.addAttribute("users",users);
		return "list";
	}

}
