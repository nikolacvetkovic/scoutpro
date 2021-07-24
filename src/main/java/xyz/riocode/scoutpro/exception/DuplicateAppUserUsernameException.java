package xyz.riocode.scoutpro.exception;

public class DuplicateAppUserUsernameException extends RuntimeException{

    public DuplicateAppUserUsernameException(){
        super("Username already taken");
    }

}
