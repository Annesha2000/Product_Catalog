package com.assignment.productcatalogservivce.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.assignment.productcatalogservivce.model.Product;
import com.assignment.productcatalogservivce.repository.ProductRepository;
import com.assignment.productcatalogservivce.service.KafkaProducer;
import com.assignment.productcatalogservivce.service.ProductService;


@RestController
@RequestMapping(value="/product")
public class ProductController 
{
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductRepository productRepository;
	
	
	
	@Autowired
    KafkaProducer kafkaProducer;

	
	/*@Value("${server.port}")
    private  String port;
	 
	 @GetMapping(value="/getPort")
	public String getPort()
	{
		return "running on :" + port;
	
	}*/
	
	@GetMapping(value="/list")
	public List<Product> getProduct() 
	{
		kafkaProducer.sendMessageToTopic("All products fetched successfully");
		return productService.findAll();
		
	}
	 
	@GetMapping(value="/listById/{id}")
	public ResponseEntity<?> getProductById(@PathVariable("id") String id) 
	{
		Product p = productService.findById(id);
		kafkaProducer.sendMessageToTopic("Product with id " +id+" fetched successfully");
		return new ResponseEntity<>(p, HttpStatus.OK);
		
	}
	
	@GetMapping(value="/byCategory")
	public List<Product> getProductByName(@RequestParam("category") String category) 
	{
		kafkaProducer.sendMessageToTopic("Products of category " +category+" fetched successfully");
		return productRepository.findByName(category);

		
	}
	
	
	
	@GetMapping("/byCategoryAndPriceRange")
    public List<Product> getProductsByNameAndPriceRange(@RequestParam("category") String category,@RequestParam("minPrice") double minPrice,@RequestParam("maxPrice") double maxPrice) 
	{
		
		kafkaProducer.sendMessageToTopic("Products of category " +category+" within the price range " +minPrice+" and "+maxPrice+" fetched successfully");

		return productRepository.findByNameAndPriceBetween(category, minPrice, maxPrice);
    }
	
	
	@DeleteMapping(value="/deleteById/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") String id) 
	{
		
		productService.deleteById(id);
		kafkaProducer.sendMessageToTopic("Product with id " +id+" deleted successfully");

		return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
		
	}
	
	@PostMapping(value="/add")
	public ResponseEntity<String> insertProduct(@RequestBody Product product) 
	{
		
		System.out.println("Product Details Saved");
		productService.save(product);
		kafkaProducer.sendMessageToTopic("product added successfully");

		return new ResponseEntity<>("Success", HttpStatus.OK);
		
}
	
		@PutMapping("/updateById/{id}")
		public ResponseEntity<?> updateProduct(@PathVariable("id") String id, @RequestBody Product product)
		{
		
			Product p = productService.findById(id);
			
			product.setId(p.getId());
				productService.save(product);
				
				kafkaProducer.sendMessageToTopic("product updated successfully");
			return new ResponseEntity<>(product, HttpStatus.OK);

			
		}
		

	

}
