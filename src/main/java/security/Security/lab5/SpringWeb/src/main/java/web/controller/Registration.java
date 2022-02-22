package web.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.database.DataBaseDAOInterface;
import web.database.UserService;
import web.database.UserServiceInterface;
import web.model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/registration")
public class Registration {
	private String errorText;
	private boolean showForm = true;
	private final UserServiceInterface userService;

	public Registration(UserServiceInterface userService) {
		super();
		this.userService = userService;
	}

	@RequestMapping(method=RequestMethod.GET)
	public String doRegistration(ModelMap modelMap) throws ServletException, IOException {
		modelMap.addAttribute("showForm", showForm);
		modelMap.addAttribute("errorText", errorText);
		return "registration";
	}

	@RequestMapping(method=RequestMethod.POST)
	public void doRegister(@RequestParam("login") String login,@RequestParam("password") String password, @RequestParam("passwordRepeat") String passwordRepeat,
			@RequestParam("name") String name, @RequestParam("gender") String gender, @RequestParam("region") String region, @RequestParam("comment") String comment,
			@RequestParam("browser") String browser,ModelMap modelMap)
			throws ServletException, IOException {
		boolean isError = false;
		Pattern pattern;
		Matcher matcher;
		errorText = "<ul style='float:right'>";
		User user = new User(login, passwordRepeat, password, name, gender, region, comment);
		if (login != null && !login.isEmpty()) {
			pattern = Pattern.compile("^(.+)@(.+)$");
			matcher = pattern.matcher(login);
			if (!matcher.matches()) {
				isError = true;
				errorText += "<li>Login doesn't match regex </li>";
			}
		} else {
			isError = true;
			errorText += "<li>Login is empty!</li>";
		}
		if (password != null && !password.isEmpty()) {
			Pattern pattern2 = Pattern.compile("[0-9]");
			Matcher matcher2 = pattern2.matcher(password);
			Pattern pattern3 = Pattern.compile("[A-Z]");
			Matcher matcher3 = pattern3.matcher(password);
			Pattern pattern4 = Pattern.compile("[!@#$%^&*`'()+-/_~{}|]");
			Matcher matcher4 = pattern4.matcher(password);
			if (!Objects.equals(password, passwordRepeat)) {
				isError = true;
				errorText += "<li>Passwords don't match!</li>";
			}
			if ((password.length() <= 8)) {
				isError = true;
				errorText += "<li>Password lenght must be more than 8!</li>";
			}
			if (!matcher2.find()) {
				isError = true;
				errorText += "<li>Password must contain a number!</li>";
			}
			if (!matcher3.find()) {
				isError = true;
				errorText += "<li>Password must contain a capital letter</li>";
			}
			if (!matcher4.find()) {
				isError = true;
				errorText += "<li>Password must contain a special symbol</li>";
			}
		} else {
			isError = true;
			errorText += "<li>Password is empty!</li>";
		}
		if (name == null || name.isEmpty()) {
			isError = true;
			errorText += "<li>Name field is empty</li>";
		}
		if (gender == null || gender.isEmpty()) {
			isError = true;
			errorText += "<li>Gender field is empty</li>";
		}
		if (region == null || region.isEmpty()) {
			isError = true;
			errorText += "<li>Region field is empty</li>";
		}
		if (comment == null || comment.isEmpty()) {
			isError = true;
			errorText += "<li>Comment is empty</li>";
		}
		if (browser == null || browser.isEmpty()) {
			isError = true;
			errorText += "<li>Browser is empty</li>";
		}

		errorText += "</ul>";
		if (!isError) {
			userService.addUser(name, login, password, gender, region, comment);
			showForm = false;
			errorText = null;
		} else {
			modelMap.addAttribute("user", user);
		}
		doRegistration(modelMap);
	}
	
	 
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher disp = req.getRequestDispatcher("WEB-INF/views/registration.jsp");
		req.setAttribute("showForm", showForm);
		req.setAttribute("errorText", errorText);
		disp.forward(req, resp);
	}

	

}
