package xyz.riocode.scoutpro.exception;

public class AppUserNotFoundException extends RuntimeException {

    public AppUserNotFoundException(){
        super("User not found");
    }
}
