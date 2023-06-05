package com.nttdata.da.activities;

import java.math.BigDecimal;
import java.util.Map;

import com.uber.cadence.activity.ActivityMethod;

public interface OrderActivities {

	@ActivityMethod
	Map<String, String> createOrder(String customerId, BigDecimal totalMoney);

	@ActivityMethod
	Map<String, String> approveOrder(String orderId);

	@ActivityMethod
	Map<String, String> rejectOrder(String orderId);
}
