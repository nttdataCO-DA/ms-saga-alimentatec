package com.nttdata.da.workflow;

import java.math.BigDecimal;

import com.uber.cadence.workflow.WorkflowMethod;

public interface CreateOrderWorkflow {
	
	@WorkflowMethod
	String createOrder(String customerId, BigDecimal totalMoney);
}
