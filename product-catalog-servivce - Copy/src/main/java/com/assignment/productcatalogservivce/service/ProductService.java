package com.assignment.productcatalogservivce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.productcatalogservivce.exception.ProductNotFoundException;
import com.assignment.productcatalogservivce.model.Product;
import com.assignment.productcatalogservivce.repository.ProductRepository;

@Service
public class ProductService 
{
	@Autowired
	private ProductRepository productRespository;
	

	public Product save(Product product) 
	{
		
		return productRespository.save(product);
		
	}
	

	public void deleteById(String id) 
	{
		if(productRespository.findById(id).isEmpty())
	
		{
			throw new ProductNotFoundException("Requested product does not exist");
		}
		else
		{
			productRespository.deleteById(id);
		}
		
	}



	public List<Product> findAll() {
		
		List<Product> list = new ArrayList<>();
		productRespository.findAll().forEach(list::add);
		return list;
	}
	
	
	
	public Product findById(String id) 
	{
		if(productRespository.findById(id).isEmpty())
		{
			throw new ProductNotFoundException("Requested product does not exist");
		}
		else 
		{
		return productRespository.findById(id).get();
		}
	}

	

}
