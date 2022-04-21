package xyz.riocode.scoutpro.scrape.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.riocode.scoutpro.scrape.model.ScrapeErrorHistory;

public interface ScrapeErrorHistoryRepository extends JpaRepository<ScrapeErrorHistory, Long> {
}
