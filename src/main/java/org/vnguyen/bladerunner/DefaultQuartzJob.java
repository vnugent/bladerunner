package org.vnguyen.bladerunner;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultQuartzJob implements Job {
	private Logger log = LoggerFactory.getLogger(DefaultQuartzJob.class);

	@Override
	public void execute(JobExecutionContext jobCtx) throws JobExecutionException {
		log.info(jobCtx.toString());
		try {
			DeleteTask task = (DeleteTask) jobCtx.getScheduler().getContext().get("delete-task");
			task.delete();
		} catch (SchedulerException e) {
			log.error(e.toString(), e);
		}
	}

}
