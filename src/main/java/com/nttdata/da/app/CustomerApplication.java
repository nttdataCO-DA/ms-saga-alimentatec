package com.nttdata.da.app;

import com.nttdata.da.activities.impl.CustomerActivitiesImpl;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.IWorkflowService;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;

public class CustomerApplication {

	
	public static void main(String[] args) {
        IWorkflowService service = new WorkflowServiceTChannel(ClientOptions.defaultInstance());

        WorkflowClientOptions workflowClientOptions = WorkflowClientOptions.newBuilder()
                .setDomain(Constants.DOMAIN)
                .build();
        WorkflowClient client = WorkflowClient.newInstance(service, workflowClientOptions);

        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(Constants.CUSTOMER_TASK_LIST);
        worker.registerActivitiesImplementations(new CustomerActivitiesImpl());
        factory.start();
    }
}
