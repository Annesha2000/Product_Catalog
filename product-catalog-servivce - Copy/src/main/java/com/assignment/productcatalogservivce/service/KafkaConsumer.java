package com.assignment.productcatalogservivce.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {



	@KafkaListener(topics = "ProductTopic", groupId = "product-group")
	public void listenToProductKafkaTopic(String messageReceived) {
		System.out.println("Message received is : " + messageReceived);
	}
}