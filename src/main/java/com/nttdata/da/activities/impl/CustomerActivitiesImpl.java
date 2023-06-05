package com.nttdata.da.activities.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nttdata.da.activities.CustomerActivities;

public class CustomerActivitiesImpl implements CustomerActivities {
	
	
	private static BigDecimal CREDIT = BigDecimal.valueOf(10000l);

	private static final Logger logger = LoggerFactory.getLogger(CustomerActivitiesImpl.class);

	public  Map<String, String>  reserveCredit(String customerId, BigDecimal amount) {
		
		Map<String, String>  result = new HashMap<>();
		
		
		
		if (CREDIT.compareTo(BigDecimal.ZERO) < 0) {
			
			CustomerActivitiesImpl.CREDIT = CustomerActivitiesImpl.CREDIT.add(amount);
			logger.info("Credit rollback to customer {}", customerId);
			result.put("Status", "Success");
			result.put("Description", "Credit return to customer account");
			result.put("Balance", CREDIT.toPlainString());
			
		}else if (amount.compareTo(CREDIT) > 0) {
			
			logger.info("Credit limit is exceeded for customer {}", customerId);
			CustomerActivitiesImpl.CREDIT = CustomerActivitiesImpl.CREDIT.subtract(amount);
			result.put("Status", "Failed");
			result.put("Description", "Credit limit is exceeded for customer");
			result.put("Balance", CREDIT.toPlainString());
			
			
		}else {
			CustomerActivitiesImpl.CREDIT = CustomerActivitiesImpl.CREDIT.subtract(amount);
			result.put("Description", "Credit limit Available!!");
			result.put("Status", "Success");
			result.put("Balance", CREDIT.toPlainString());
		}
		logger.info("Credit reserved for customer {}", customerId);
		return result;
	}


}
