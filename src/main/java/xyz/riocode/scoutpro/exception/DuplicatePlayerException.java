package xyz.riocode.scoutpro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Nikola Cvetkovic
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicatePlayerException extends RuntimeException{
    
    public DuplicatePlayerException(){
        super("Player exists");
    }
}
