package web.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;

import web.database.mapper.ProductRowMapper;
import web.model.Product;

@Service
public class ProductDBService implements DataProductDAOInterface{
	
	//private final DataSource dataSource;
	private final JdbcTemplate jdbcTemplate;
	private static final Logger LOGGER = Logger.getLogger(ProductDBService.class.getName());
	public static final String SELECT_PRODUCTS = "SELECT p.id, p.name, description, price FROM products p ";
	public static final String SELECT_PRODUCTS_BY_ID = SELECT_PRODUCTS + "WHERE id = ?";
	public static final String SELECT_PRODUCTS_BY = SELECT_PRODUCTS + "JOIN producttocategory pc on p.id = pc.product_id JOIN categories c on pc.category_id = c.id WHERE c.name = ? ";
	
		
	@Autowired
	public ProductDBService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	

	public List<Product> getProducts() {
		List<Product> products;
		LOGGER.info("Starting query");
		products = jdbcTemplate.query(SELECT_PRODUCTS, new ProductRowMapper());
		LOGGER.info("Query success");
		return products;
	}

	public Product getProductById(int id) {
		Product product;
		List<Product> products;
		LOGGER.info("Starting query");
		products = jdbcTemplate.query(SELECT_PRODUCTS_BY_ID, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, id);
				
			}}, new ProductRowMapper());
		LOGGER.info("Query success");
		return products.get(0);

	}
	public Product getProductById(String id) {
		return getProductById(Integer.parseInt(id));
	}

	public List<Product> getProductsByCategory(String categoryName) {
		List<Product> products;
		LOGGER.info("Starting query");
		products = jdbcTemplate.query(SELECT_PRODUCTS_BY, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, categoryName);
				
			}}, new ProductRowMapper());
		LOGGER.info("Query success");
		return products;
	}


	public JdbcTemplate getTemplate() {
		return jdbcTemplate;
	}
	
	
}
