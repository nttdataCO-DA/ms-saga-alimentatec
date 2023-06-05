package com.nttdata.da.activities.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nttdata.da.activities.OrderActivities;

public class OrderActivitiesImpl implements OrderActivities {

	private static final Logger logger = LoggerFactory.getLogger(OrderActivitiesImpl.class);

	public Map<String, String> createOrder(String customerId, BigDecimal amount) {

		Map<String, String> result = new HashMap<>();

		var orderId = UUID.randomUUID().toString();
		result.put("Status", "Created");
		result.put("Description", orderId);

		logger.info("Order {} created in pending state", orderId);

		return result;
	}

	public Map<String, String> approveOrder(String orderId) {
		
		Map<String, String> result = new HashMap<>();

		result.put("Status", "Success");
		result.put("Description", "User order is Aproved!!");
		
		logger.info("Order {} approved", orderId);
		
		return result;
	}

	public Map<String, String> rejectOrder(String orderId) {
		
		Map<String, String> result = new HashMap<>();
		
		result.put("Status", "Canceled");
		result.put("Description", "User has Credit Available!!");
		logger.info("Order {} rejected", orderId);
		
		return result;
	}
}
