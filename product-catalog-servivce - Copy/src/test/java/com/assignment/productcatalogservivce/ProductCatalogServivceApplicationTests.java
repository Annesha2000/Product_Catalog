package com.assignment.productcatalogservivce;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import com.assignment.productcatalogservivce.controller.ProductController;
import com.assignment.productcatalogservivce.exception.ProductNotFoundException;
import com.assignment.productcatalogservivce.model.Product;
import com.assignment.productcatalogservivce.repository.ProductRepository;
import com.assignment.productcatalogservivce.service.ProductService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.assignment.productcatalogservivce.service.KafkaProducer;


@SpringBootTest
class ProductCatalogServivceApplicationTests 
{

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductController controller;
	 
	@Autowired
    KafkaProducer kafkaProducer;
	
	@MockBean
	private ProductRepository productRepository;
	
	Product product = new Product();
	
	List<Product> productList=new ArrayList<Product>();
	
	@BeforeEach
	public void setup() 
	{
		product.setId("abcd");
		
		product.setName("Mobile");	
		product.setDescription("Android Mobile");
		product.setPrice(20000);
		product.setAvailability("yes");
		
		productList.add(product);
	}
	
	@Test
    void testIdGetterAndSetter() 
	{
        
        product.setId("abcd");
        assertEquals("abcd", product.getId());
    }
	
	
    @Test
     void testNameGetterAndSetter() 
    {
        
        product.setName("Test Product");
        assertEquals("Test Product", product.getName());
    }
    
    @Test
     void testDescriptionGetterAndSetter() 
    {
        
        product.setDescription("A sample product description");
        assertEquals("A sample product description", product.getDescription());
    }
    
    
    @Test
     void testPriceGetterAndSetter() 
    {
        
        product.setPrice(19.99);
        assertEquals(19.99, product.getPrice()); 
    }
    
    @Test
     void testAvailabilityGetterAndSetter() 
    {
        
        product.setAvailability("yes");
        assertEquals("yes", product.getAvailability()); 
    }


    
    /*@Test
	 void productNotFoundTest()
    {
    	Mockito.when(productService.findById("abcd")).thenReturn(false);
		//assertEquals(product, productService.findById("abcd"));
		
		ResponseEntity<?> responseEntity = controller.getProductById("abcd");
		//assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Requested product does not exist", responseEntity.getBody());

		
	}*/
    
    
	
	@Test
	 void getProductDetailsTest() {
		Mockito.when(productService.findAll()).thenReturn(productList);
		assertEquals(1, productService.findAll().size());
		
		List<Product> productList1 = controller.getProduct();
		assertEquals(productList1.size(),productList.size());
		
		kafkaProducer.sendMessageToTopic("All products fetched successfully");
	}
	
	
	@Test
	 void getProductsDetailsByIdTest() 
	{
		
		
		Mockito.when(productRepository.findById("abcd")).thenReturn(Optional.of(product));
		assertEquals(product, productService.findById("abcd"));
		
		ResponseEntity<?> responseEntity = controller.getProductById("abcd");
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());


		kafkaProducer.sendMessageToTopic("All products fetched successfully");
	}
	
	
	@Test
	 void getProductDetailsByCategoryTest() 
	{
				
		Mockito.when(productRepository.findByName("Mobile")).thenReturn(productList);
		assertEquals(1, productRepository.findByName("Mobile").size());
		
		List<Product> productList1  = controller.getProductByName("Mobile");
		assertEquals(productList.size() ,productList1.size());


		kafkaProducer.sendMessageToTopic("All products fetched successfully");
	}
	
	@Test
	 void getProductDetailsByCategoryAndPriceRangeTest() 
	{
				
		Mockito.when(productRepository.findByNameAndPriceBetween("Mobile",10000,25000)).thenReturn(productList);
		assertEquals(1, productRepository.findByNameAndPriceBetween("Mobile",10000,25000).size());
		
		List<Product> productList1  = controller.getProductsByNameAndPriceRange("Mobile",10000,25000);
		assertEquals(productList.size() ,productList1.size());


		kafkaProducer.sendMessageToTopic("All products fetched successfully");
	}
	
	
	
	@Test
	 void insertProductDetailsTest() {
		Mockito.when(productRepository.save(product)).thenReturn(product);
		assertEquals(product,productService.save(product));
		
		ResponseEntity<String> responseEntity = controller.insertProduct(product);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		kafkaProducer.sendMessageToTopic("product added successfully");

	}
	
	
	@Test
      void findByIdTest() {
	Mockito.when(productRepository.findById("abcd")).thenReturn(Optional.of(product));
		assertEquals(product, productService.findById("abcd"));
		kafkaProducer.sendMessageToTopic("Product with id abcd found successfully");

	}
	
	
	@Test
	 void deleteByIdTest() {
		Mockito.when(productRepository.findById("abcd")).thenReturn(Optional.of(product));
		productService.deleteById("abcd");
		
		ResponseEntity<String> responseEntity = controller.deleteProduct("abcd");
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		// Mockito.verify(productRepository, Mockito.times(1)).deleteById("abcd");
			kafkaProducer.sendMessageToTopic("Product with id abcd deleted successfully");

	}
	
	@Test
	 void updateByIdTest() 
	{
		
		Mockito.when(productRepository.findById("abcd")).thenReturn(Optional.of(product));
		product.setPrice(21000);
		
		ResponseEntity<?> responseEntity = controller.updateProduct("abcd",product);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		
			kafkaProducer.sendMessageToTopic("Product with id abcd updated successfully");

	}
}
