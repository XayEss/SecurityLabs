package web.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import web.database.UserService;
import web.database.UserServiceInterface;

@Controller
@RequestMapping("/profile")
public class Profile {
	private static final String AUTHORIZED = "authorized";
	private static final String LOGIN = "login";
	private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

	private UserServiceInterface userService;

	
	@GetMapping()
	public String showProfile(ModelMap model) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		String loggedInUserName = (String) session.getAttribute(LOGIN);
		if(loggedInUserName != null && !loggedInUserName.isEmpty()) {
			LOGGER.info(loggedInUserName);
			model.addAttribute("user", userService.getCurrentUser(loggedInUserName));
		}
		return "profile";
	}

	@Autowired
	public void setUserService(UserServiceInterface userService) {
		this.userService = userService;
	}
	
	

}
