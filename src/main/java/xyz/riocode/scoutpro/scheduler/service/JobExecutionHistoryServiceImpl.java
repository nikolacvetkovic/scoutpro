package xyz.riocode.scoutpro.scheduler.service;

import org.springframework.stereotype.Service;
import xyz.riocode.scoutpro.scheduler.model.JobExecutionHistory;
import xyz.riocode.scoutpro.scheduler.repository.JobExecutionHistoryRepository;

import java.util.List;

@Service
public class JobExecutionHistoryServiceImpl implements JobExecutionHistoryService{

    private final JobExecutionHistoryRepository jobExecutionHistoryRepository;

    public JobExecutionHistoryServiceImpl(JobExecutionHistoryRepository jobExecutionHistoryRepository) {
        this.jobExecutionHistoryRepository = jobExecutionHistoryRepository;
    }

    @Override
    public List<JobExecutionHistory> getAll() {
        return jobExecutionHistoryRepository.findAll();
    }
}
