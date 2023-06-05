package com.nttdata.da.activities;

import java.math.BigDecimal;
import java.util.Map;

import com.uber.cadence.activity.ActivityMethod;

public interface CustomerActivities {

	@ActivityMethod
    Map<String, String> reserveCredit(String customerId, BigDecimal amount);
}
