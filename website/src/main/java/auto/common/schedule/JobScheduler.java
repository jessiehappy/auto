package auto.common.schedule;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import lombok.extern.apachecommons.CommonsLog;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import auto.Properties;

@CommonsLog
@Component
public class JobScheduler {
    
    private Scheduler scheduler;
    
    @Autowired
    private List<SimpleJob> jobs;

    public JobScheduler() throws Exception {
        scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
    }

    @PostConstruct
    public void init() throws Exception {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (Properties.isJobOn) {
                    for (SimpleJob job : jobs) {
                        try {
                            JobScheduler.this.schedule(job);
                        } catch (Exception e) {
                            log.error(e, e);
                        }
                    }
                }
            }
        }, 5000L);
        
    }

    private void schedule(SimpleJob job) throws Exception {
        log.info("Schedule job: cron=" + job.getQuartzTime() + ", job=" + job.getClass().getSimpleName());
        JobDetail jobDetail = newJob(JobAdapter.class).build();
        jobDetail.getJobDataMap().put(SimpleJob.class.getSimpleName(), job);
        scheduler.scheduleJob(jobDetail,
                newTrigger().withSchedule(cronSchedule(job.getQuartzTime())).build());
    }

    /**
     * Adapt {@link SimpleJob} to {@link Job}.
          */
    public static class JobAdapter implements Job {

        @Override
        public void execute(JobExecutionContext context)
                throws JobExecutionException {
            JobDataMap map = context.getMergedJobDataMap();
            SimpleJob job = (SimpleJob) map
                    .get(SimpleJob.class.getSimpleName());
            log.info("Run job: " + job.getClass().getSimpleName());
            try {
                job.run();
            } catch (Exception e) {
                log.error("Run job failed: " + job.getClass().getSimpleName());
            }
        }

    }

}
