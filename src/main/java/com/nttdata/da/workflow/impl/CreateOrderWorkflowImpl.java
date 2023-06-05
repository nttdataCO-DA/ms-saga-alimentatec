package com.nttdata.da.workflow.impl;

import java.math.BigDecimal;
import java.time.Duration;

import com.nttdata.da.activities.CustomerActivities;
import com.nttdata.da.activities.OrderActivities;
import com.nttdata.da.app.Constants;
import com.nttdata.da.workflow.CreateOrderWorkflow;
import com.uber.cadence.activity.ActivityOptions;
import com.uber.cadence.workflow.ActivityException;
import com.uber.cadence.workflow.Saga;
import com.uber.cadence.workflow.Workflow;

public class CreateOrderWorkflowImpl implements CreateOrderWorkflow {

    private final ActivityOptions customerActivityOptions = new ActivityOptions.Builder()
            .setTaskList(Constants.CUSTOMER_TASK_LIST)
            .setScheduleToCloseTimeout(Duration.ofHours(2))
            .build();
    private final CustomerActivities customerActivities =
            Workflow.newActivityStub(CustomerActivities.class, customerActivityOptions);

    private final ActivityOptions orderActivityOptions = new ActivityOptions.Builder()
            .setTaskList(Constants.ORDER_TASK_LIST)
            .setScheduleToCloseTimeout(Duration.ofHours(2))
            .build();
    private final OrderActivities orderActivities =
            Workflow.newActivityStub(OrderActivities.class, orderActivityOptions);

    @Override
    public String createOrder(String customerId, BigDecimal amount) {
        Saga.Options sagaOptions = new Saga.Options.Builder().build();
        Saga saga = new Saga(sagaOptions);
        try {
            String orderId = orderActivities.createOrder(customerId, amount).get("Description");
            saga.addCompensation(orderActivities::rejectOrder, orderId);
            saga.addCompensation(customerActivities::reserveCredit, customerId, amount);

            if (customerActivities.reserveCredit(customerId, amount).get("Status").equals("Success")) {
                orderActivities.approveOrder(orderId);
                return orderId;
            } else {
                saga.compensate();
                throw new IllegalStateException("Failed to reserve credit");
            }
        } catch (ActivityException e) {
            saga.compensate();
            throw e;
        }
    }
}
