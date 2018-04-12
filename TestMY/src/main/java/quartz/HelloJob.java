package quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Job实例
 *
 * @author cheng.jin
 * @since 2018年04月13日
 */
public class HelloJob implements Job{
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }
}
