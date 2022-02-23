package web.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import web.database.DataProductDAOInterface;
import web.database.ProductDBService;
import web.model.Product;

@Controller
@RequestMapping("/product")
public class ProductServlet {
	private final DataProductDAOInterface productService;
       
	public ProductServlet(DataProductDAOInterface productService) {
		super();
		this.productService = productService;
	}

	@GetMapping
	public String printProduct(@RequestParam("id") int id, ModelMap modelMap) throws ServletException, IOException {
    	Product product = productService.getProductById(id);
    	modelMap.addAttribute("product", product);
		return "product";
    }
	
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/views/product.jsp");
    	int id = Integer.parseInt(req.getParameter("id"));
    	dispatcher.forward(req, resp);
    }
    
    
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doGet(req, resp);
    }

}
