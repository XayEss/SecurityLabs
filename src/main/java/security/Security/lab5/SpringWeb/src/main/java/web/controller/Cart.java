package web.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.database.DataProductDAOInterface;
import web.database.ProductDBService;
import web.model.Product;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Authenticator.RequestorType;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
@RequestMapping("/cart")
public class Cart {
	
	private final DataProductDAOInterface dbService;

	public Cart(DataProductDAOInterface dbService) {
		super();
		this.dbService = dbService;
	}
	@GetMapping
	public String printCart(ModelMap modelMap){
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		Object productCart = session.getAttribute("cart");
		Map<Product, Integer> cart = (Map<Product, Integer>) productCart;
		if (cart == null) {
			cart = new HashMap<>();
		}
		session.setAttribute("productMap", cart);
		return "cart";

	}
	@PostMapping
	@ResponseBody
	public String createCart(@RequestParam(required = false) String id, @RequestParam("amount") String amountStr, @RequestParam(value = "change", required=false) String change) throws ServletException, IOException {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		Product product;
		int amount = Integer.parseInt(amountStr);
		Object productCart = session.getAttribute("cart");
		Map<Product, Integer> cart = (Map<Product, Integer>) productCart;
		if (id != null && !id.isEmpty()) {
			product = dbService.getProductById(id);
			if (cart == null) {
				cart = new HashMap<>();
			}
			if (cart.containsKey(product)) {
				cart.put(product, cart.get(product) + amount);
			} else {
				cart.put(product, amount);
			}
			if (cart.get(product) == 0) {
				cart.remove(product);
			}
			session.setAttribute("cart", cart);
			session.setAttribute("cartSize", count(cart));
			//attr.getResponse().getWriter().write(String.valueOf(count(cart)));
			//responsePrinter();
		} else {
			if (change != null) {
				product = dbService.getProductById(change);
				if (amount != 0) {
					cart.put(product, amount);
				} else {
					cart.remove(product);
				}
			}
			session.setAttribute("cartSize", count(cart));
			session.setAttribute("productMap", cart);
			//attr.getResponse().getWriter().write(String.valueOf(count(cart)));
		}
		return String.valueOf(count(cart));
		
	}
	
	@ResponseBody
	public String responsePrinter(String str)  {
	  return "str";
	}

	private int count(Map<Product, Integer> cart) {
		int sum = 0;
		for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
			sum += entry.getValue();
		}
		return sum;
	}

}
