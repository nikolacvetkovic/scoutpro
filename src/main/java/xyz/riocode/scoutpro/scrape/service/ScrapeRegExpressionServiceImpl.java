package xyz.riocode.scoutpro.scrape.service;

import org.springframework.stereotype.Service;
import xyz.riocode.scoutpro.exception.ScrapeReqExpressionNotFound;
import xyz.riocode.scoutpro.scrape.enums.ScrapeField;
import xyz.riocode.scoutpro.scrape.model.ScrapeRegExpression;
import xyz.riocode.scoutpro.scrape.repository.ScrapeRegExpressionRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class ScrapeRegExpressionServiceImpl implements ScrapeRegExpressionService {

    private final ScrapeRegExpressionRepository scrapeRegExpressionRepository;

    public ScrapeRegExpressionServiceImpl(ScrapeRegExpressionRepository scrapeRegExpressionRepository) {
        this.scrapeRegExpressionRepository = scrapeRegExpressionRepository;
    }

    @Override
    public ScrapeRegExpression getByFieldName(ScrapeField scrapeField) {
        return scrapeRegExpressionRepository.findByFieldName(scrapeField);
    }

    @Override
    public Set<ScrapeRegExpression> getAll() {
        return new HashSet<>(scrapeRegExpressionRepository.findAll());
    }

    @Override
    public void update(ScrapeRegExpression scrapeRegExpression) {
        ScrapeRegExpression foundExpression = scrapeRegExpressionRepository.findByFieldName(scrapeRegExpression.getFieldName());
        if (foundExpression == null) throw new ScrapeReqExpressionNotFound(scrapeRegExpression);
        foundExpression.setRegex(scrapeRegExpression.getRegex());
        scrapeRegExpressionRepository.save(foundExpression);
    }
}
