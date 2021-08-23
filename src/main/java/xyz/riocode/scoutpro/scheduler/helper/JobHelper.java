package xyz.riocode.scoutpro.scheduler.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JobHelper {

    public JobDetail createJob(Class<? extends QuartzJobBean> jobClass,
                               String jobName,
                               String jobGroup,
                               String jobData) {
        return JobBuilder.newJob(jobClass)
                .withIdentity(jobName, jobGroup)
                .storeDurably()
                .usingJobData(createJobDataMap(jobData))
                .build();
    }

    public CronTrigger createCronTrigger(JobKey jobKey,
                                     Date startTime,
                                     Date endTime,
                                     String cronExpression) {
        return TriggerBuilder.newTrigger()
                .withIdentity(jobKey.getName(), jobKey.getGroup())
                .forJob(jobKey)
                .startAt(startTime)
                .endAt(endTime)
                .withSchedule(CronScheduleBuilder
                        .cronSchedule(cronExpression)
                        .withMisfireHandlingInstructionFireAndProceed())
                .build();
    }

    public SimpleTrigger createSimpleTrigger(JobKey jobKey,
                                             Date startTime,
                                             Date endTime,
                                             int repeatIntervalInSeconds,
                                             int repeatCount) {
        return TriggerBuilder.newTrigger()
                .withIdentity(jobKey.getName(), jobKey.getGroup())
                .forJob(jobKey)
                .startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.
                        simpleSchedule()
                        .withIntervalInSeconds(repeatIntervalInSeconds)
                        .withRepeatCount(repeatCount))
                .endAt(endTime)
                .build();
    }

    public JobDataMap createJobDataMap(String json){
        JobDataMap jobDataMap = new JobDataMap();
        if (json != null && json.length() > 0) {
            Gson gson = new GsonBuilder().create();
            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
            for(String fieldName : jsonObject.keySet()){
                jobDataMap.put(fieldName, jsonObject.get(fieldName).getAsString());
            }
        }
        return jobDataMap;
    }

}
