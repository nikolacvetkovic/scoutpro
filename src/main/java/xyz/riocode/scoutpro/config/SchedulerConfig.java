package xyz.riocode.scoutpro.config;

import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class SchedulerConfig {

    private final ApplicationContext applicationContext;
    private final DataSource dataSource;
    private final QuartzProperties quartzProperties;

    public SchedulerConfig(ApplicationContext applicationContext, DataSource dataSource, QuartzProperties quartzProperties) {
        this.applicationContext = applicationContext;
        this.dataSource = dataSource;
        this.quartzProperties = quartzProperties;
    }

    @Bean
    public SpringBeanJobFactory jobFactory(){
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean scheduler(){
        Properties properties = new Properties();
        properties.putAll(quartzProperties.getProperties());

        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setOverwriteExistingJobs(true);
        schedulerFactory.setDataSource(dataSource);
        schedulerFactory.setQuartzProperties(properties);
        schedulerFactory.setJobFactory(jobFactory());
        return schedulerFactory;
    }

}
