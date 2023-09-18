package com.assignment.productcatalogservivce.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.assignment.productcatalogservivce.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>
{
	List<Product> findByName(String category);
	
	
	
	List<Product> findByNameAndPriceBetween(String category, double minPrice, double maxPrice);

}

