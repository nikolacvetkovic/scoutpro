package xyz.riocode.scoutpro.exception;

import xyz.riocode.scoutpro.scrape.model.ScrapeRegExpression;

public class ScrapeReqExpressionNotFound extends RuntimeException {

    public ScrapeReqExpressionNotFound(){
        super();
    }

    public ScrapeReqExpressionNotFound(ScrapeRegExpression scrapeRegExpression){
        super(scrapeRegExpression.getFieldName().toString());
    }
}
