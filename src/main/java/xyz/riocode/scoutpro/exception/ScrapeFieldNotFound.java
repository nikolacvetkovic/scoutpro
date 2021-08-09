package xyz.riocode.scoutpro.exception;

import xyz.riocode.scoutpro.scrape.model.ScrapeField;

public class ScrapeFieldNotFound extends RuntimeException {

    public ScrapeFieldNotFound(){
        super();
    }

    public ScrapeFieldNotFound(ScrapeField scrapeField){
        super(scrapeField.getName());
    }
}
