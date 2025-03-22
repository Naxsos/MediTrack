package org.example;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AllgemeinerScheduler {

    /*public AllgemeinerScheduler() throws SchedulerException {

        JobDetail jobDetail = JobBuilder.newJob()
                .withIdentity("myDailyJob", "group1")
                .build();

        // 2) Build a Cron Trigger for 8:00 AM daily
        Trigger dailyAtEightTrigger = TriggerBuilder.newTrigger()
                .withIdentity("myDailyTrigger", "group1")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("2 * * * * ?")
                                .inTimeZone(TimeZone.getTimeZone("Europe/Berlin"))
                )
                .build();

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, dailyAtEightTrigger);
    }

    public class CronJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {

            // SendeErinnerungMedikamentlaeuftAb sendeErinnerungMedikamentlaeuftAb = new SendeErinnerungMedikamentlaeuftAb()

        }

    }

    public AllgemeinerScheduler(MedikamenteSpeicher medikamenteSpeicher) {
        SendeErinnerungScheduler sendeErinnerungScheduler = new SendeErinnerungScheduler(medikamenteSpeicher);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> sendeErinnerungScheduler.sendeErinnerung();

        // Schedule with fixed rate of 10 seconds
        // Initial delay = 0, period = 10 seconds
        executor.scheduleAtFixedRate(task, 0, 10, TimeUnit.SECONDS);


    }*/

}
