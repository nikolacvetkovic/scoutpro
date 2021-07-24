package xyz.riocode.scoutpro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Nikola Cvetkovic
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Connection to external resource failed. Check the player's urls")
public class GetDocumentConnectionException extends RuntimeException{
        
    public GetDocumentConnectionException(Throwable t) {
        super(t);
    }
    
    public GetDocumentConnectionException(String message, Throwable t) {
        super(message, t);
    }
}
