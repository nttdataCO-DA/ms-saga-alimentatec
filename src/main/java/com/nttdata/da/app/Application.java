package com.nttdata.da.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.nttdata.da.activities.impl.OrderActivitiesImpl;
import com.nttdata.da.workflow.impl.CreateOrderWorkflowImpl;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.IWorkflowService;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;

@SpringBootApplication
@ComponentScan(basePackages = "com.nttdata.*")
public class Application {
	
	
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
	
	
	@Bean
    WorkflowClient workflowClient() {
        IWorkflowService service = new WorkflowServiceTChannel(ClientOptions.defaultInstance());

        WorkflowClientOptions workflowClientOptions = WorkflowClientOptions.newBuilder()
                .setDomain(Constants.DOMAIN)
                .build();
        return WorkflowClient.newInstance(service, workflowClientOptions);
    }
	
	
	@Bean
    CommandLineRunner commandLineRunner(WorkflowClient workflowClient) {
        return args -> {
            WorkerFactory factory = WorkerFactory.newInstance(workflowClient);
            Worker worker = factory.newWorker(Constants.ORDER_TASK_LIST);
            worker.registerActivitiesImplementations(new OrderActivitiesImpl());
            worker.registerWorkflowImplementationTypes(CreateOrderWorkflowImpl.class);
            
            factory.start();
        };
    }

	
	
	
}
