package xyz.riocode.scoutpro.scrape.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.riocode.scoutpro.scrape.model.ScrapeError;

public interface ScrapeErrorRepository extends JpaRepository<ScrapeError, Long> {
}
