package org.vnguyen.bladerunner;

import org.apache.stratos.kubernetes.api.client.v2.KubernetesApiClient;
import org.apache.stratos.kubernetes.api.exceptions.KubernetesClientException;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main  {
	private Logger log = LoggerFactory.getLogger(Main.class);
	private KubernetesApiClient kubernetesClient ;
	private String filterStrategy;
	
	public Main() throws SchedulerException {
		String apiEndpoint = Utils.getEnvStrict("KUBERNETES_API_ENDPOINT");
		log.info("Kubernetes endpoint: {}", apiEndpoint);
		kubernetesClient = new  KubernetesApiClient(apiEndpoint);
		preflightChecks();
		startCron();
	}
	
	public void preflightChecks() {
		try {
			kubernetesClient.getAllPods();
		} catch (KubernetesClientException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Main setFilterStrategy(String className) throws ClassNotFoundException {
		filterStrategy = className;
		return this;
	}
	
	String getFilter() {
		return this.filterStrategy;
	}

	public KubernetesApiClient api() {
		return kubernetesClient;
	}
	
	public void startCron() throws SchedulerException {
		JobDetail jobDetail = JobBuilder.newJob(DefaultJob.class)
			    .withIdentity("job1", "group1")
			    .build();
		
		
		Trigger trigger = TriggerBuilder.newTrigger()
				    .withIdentity("bladerunner", "group1")
				    .withSchedule(CronScheduleBuilder.cronSchedule("0 * * ? * MON-FRI"))
				    .build();
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = sf.getScheduler();
		scheduler.getContext().put("delete-task", new DeleteTask(this));
		scheduler.scheduleJob(jobDetail, trigger);
		scheduler.start();
	
	}
	
    public static void main( String[] args )    {
    	try {
			new Main();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
