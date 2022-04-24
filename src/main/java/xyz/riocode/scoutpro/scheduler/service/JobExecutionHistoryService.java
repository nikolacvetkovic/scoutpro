package xyz.riocode.scoutpro.scheduler.service;

import xyz.riocode.scoutpro.scheduler.model.JobExecutionHistory;

import java.util.List;

public interface JobExecutionHistoryService {
    List<JobExecutionHistory> getAll();
}
