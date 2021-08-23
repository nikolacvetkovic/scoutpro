package xyz.riocode.scoutpro.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.riocode.scoutpro.scheduler.model.JobInfo;

public interface JobInfoRepository extends JpaRepository<JobInfo, Long> {

    JobInfo findByJobNameAndJobGroup(String jobName, String jobGroup);

}
