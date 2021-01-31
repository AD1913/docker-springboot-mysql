package com.spring.dao;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.spring.domain.Data;
import com.spring.domain.Products;

@Repository
public class ProductDaoImpl extends JdbcDaoSupport implements ProductDao{

	@Autowired
	DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}
	
	@Override
	public List<Map<String, Object>> getProductSummary() {
		// TODO Auto-generated method stub
		
		String sql = "SELECT l3category, location, sum(qty) qty FROM products group by location, l3category";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

		return rows;
	}

	@Override
	public void insertProducts(Data data) {
		String sql = "INSERT INTO products " + "(id, sku, description, "
				+ "l1category, l2category, l3category, l4category, l5category, price, location, qty) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		String id = data.getTransmissionsummary().getId();
		for (Products product: data.getProducts()) {
		
		List<String> categories = 
				Arrays.asList(product.getCategory().split("\\s*>\\s*"));
			
		getJdbcTemplate().update(sql, new Object[] { 
				id, product.getSku(), product.getDescription(), 
				categories!=null && categories.size() > 0 ?categories.get(0):null,
				categories!=null && categories.size() > 1 ?categories.get(1):null,
				categories!=null && categories.size() > 2 ?categories.get(2):null,
				categories!=null && categories.size() > 3 ?categories.get(3):null,
				categories!=null && categories.size() > 4 ?categories.get(4):null,
				product.getPrice(), product.getLocation(), product.getQty()});
		}
	}

	@Override
	public boolean isFileProcessed(String id) {
		// TODO Auto-generated method stub
		
		String sql = "SELECT id FROM products where id = '" + id +"'";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
		
		if(null != rows && rows.size() > 0) 
			return true;
		
		return false;
	}

}
