package xyz.riocode.scoutpro.scrape.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.riocode.scoutpro.scrape.model.ScrapeSite;

public interface ScrapeSiteRepository extends JpaRepository<ScrapeSite, Long> {

    ScrapeSite findByHostname(String hostname);

}
