package com.spring.dao;

import java.util.List;
import java.util.Map;

import com.spring.domain.Data;

public interface ProductDao {

	boolean isFileProcessed(String id);
	List<Map<String, Object>> getProductSummary();
	void insertProducts(Data data);
	
}
