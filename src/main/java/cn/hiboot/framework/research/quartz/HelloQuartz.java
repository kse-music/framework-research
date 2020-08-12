package cn.hiboot.framework.research.quartz;

import org.quartz.*;

import java.util.Date;

@DisallowConcurrentExecution
public class HelloQuartz implements Job{
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDetail detail = context.getJobDetail();
		Object name = detail.getJobDataMap().get("name");
		System.out.println("say hello to " + name + " at " + new Date());
	}

}
