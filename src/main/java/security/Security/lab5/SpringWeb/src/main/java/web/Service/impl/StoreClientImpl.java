package web.Service.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import web.Service.StoreClient;
import web.model.Product;

@Service
public class StoreClientImpl implements StoreClient {
	
	private static final String url = "http://localhost:8081/";
	private final ObjectMapper mapper;
	
	

	public StoreClientImpl(final ObjectMapper mapper) {
		super();
		this.mapper = mapper;
	}

	@Override
	public Product addProduct(Product product) {
		return null;
	}

	@Override
	public Optional<Product> getProduct(int id) {
		String filePath = "productcontroller?id=";
		Product product = null;
		try {
			URL urlObj = new URL(url+filePath+id);
			URLConnection urlCon = urlObj.openConnection();
			InputStream inputStream = urlCon.getInputStream();
			BufferedInputStream reader = new BufferedInputStream(inputStream);
			product = mapper.reader().readValue(inputStream, Product.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(product);
	}

	@Override
	public List<Product> getAll() {
		return null;
	}

	@Override
	public Product updateProduct(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dropProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

}
