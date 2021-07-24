package xyz.riocode.scoutpro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Nikola Cvetkovic
 */

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ParseException extends RuntimeException{
    
    public ParseException(String s){
        super(s);
    }
    
//    public ParseException(Throwable t){
//        super(t);
//    }
}
