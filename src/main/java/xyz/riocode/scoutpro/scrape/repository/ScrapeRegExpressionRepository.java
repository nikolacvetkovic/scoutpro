package xyz.riocode.scoutpro.scrape.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.riocode.scoutpro.scrape.enums.ScrapeField;
import xyz.riocode.scoutpro.scrape.model.ScrapeRegExpression;

public interface ScrapeRegExpressionRepository extends JpaRepository<ScrapeRegExpression, String> {
    ScrapeRegExpression findByFieldName(ScrapeField scrapeField);
}
