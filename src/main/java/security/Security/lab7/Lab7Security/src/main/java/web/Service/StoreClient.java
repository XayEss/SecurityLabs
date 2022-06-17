package web.Service;

import java.util.List;
import java.util.Optional;


import web.model.Product;

//@FeignClient(name="product", url="localhost:8081/productcontroller") 
public interface StoreClient {
	
	public Product addProduct(Product product);
	Optional<Product> getProduct(int id);
	List<Product> getAll();
	Product updateProduct(Product product);
	void dropProduct(Product product);
}
