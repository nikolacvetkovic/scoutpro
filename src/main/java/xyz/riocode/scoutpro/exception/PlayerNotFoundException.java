package xyz.riocode.scoutpro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Nikola Cvetkovic
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PlayerNotFoundException extends RuntimeException{
    
    public PlayerNotFoundException(){
        super();
    }
    
    public PlayerNotFoundException(String parameterName, Object parameterValue){
        super("Player not found for parameter [" + parameterName + "=" + String.valueOf(parameterValue) + "]");
    }
}
