package xyz.riocode.scoutpro.scrape.service;

import xyz.riocode.scoutpro.scrape.enums.ScrapeField;
import xyz.riocode.scoutpro.scrape.model.ScrapeRegExpression;

import java.util.Set;

public interface ScrapeRegExpressionService {
    ScrapeRegExpression getByFieldName(ScrapeField scrapeField);
    Set<ScrapeRegExpression> getAll();
    void update(ScrapeRegExpression scrapeRegExpression);
}
