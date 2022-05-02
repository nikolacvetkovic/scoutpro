package xyz.riocode.scoutpro.scrape.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.riocode.scoutpro.scrape.model.ScrapeSite;

import java.time.LocalDateTime;
import java.util.List;

public interface ScrapeSiteRepository extends JpaRepository<ScrapeSite, Long> {

    ScrapeSite findByHostname(String hostname);
    @Query(value = "SELECT s FROM ScrapeSite s " +
            "WHERE s.lastChecked < :lastChecked")
    List<ScrapeSite> findByLastCheckedBefore(LocalDateTime lastChecked);

}
