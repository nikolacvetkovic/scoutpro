package xyz.riocode.scoutpro.error;

/**
 *
 * @author Nikola Cvetkovic
 */

public class ConstraintViolationSubAppErr extends SubAppErr{
    private String entity;
    private String attribute;
    private String invalidValue;
    private String message;

    public ConstraintViolationSubAppErr(String entity, String attribute, String invalidValue, String message) {
        this.entity = entity;
        this.attribute = attribute;
        this.invalidValue = invalidValue;
        this.message = message;
    }
    
    
}
