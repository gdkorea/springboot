package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@GetMapping("form")
	public String form(HttpSession session)
	{
		if(!HttpSessionUtils.isLoginUser(session))
		{
			return "/users/login";
		}
		return "/qna/form";
	}
	

	@PostMapping("")
	public String Create(String title, String contents, HttpSession session)
	{
		if(!HttpSessionUtils.isLoginUser(session))
		{
			return "/users/login";
		}
		
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(sessionUser.getUserId(), title, contents);
		
		questionRepository.save(newQuestion);
		
		return "redirect:/";
	}


}
