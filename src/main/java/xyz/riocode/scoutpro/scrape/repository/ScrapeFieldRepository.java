package xyz.riocode.scoutpro.scrape.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.riocode.scoutpro.scrape.model.ScrapeField;

import java.util.List;

public interface ScrapeFieldRepository extends JpaRepository<ScrapeField, Long> {
    List<ScrapeField> findByScrapeSite_Id(Long scrapeSiteId);
    ScrapeField findByName(String name);
    List<ScrapeField> findByScrapeSite_Name(String scrapeSiteName);
}
