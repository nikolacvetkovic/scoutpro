package xyz.riocode.scoutpro.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.riocode.scoutpro.scheduler.model.JobExecutionHistory;

public interface JobExecutionHistoryRepository extends JpaRepository<JobExecutionHistory, Long> {
}
