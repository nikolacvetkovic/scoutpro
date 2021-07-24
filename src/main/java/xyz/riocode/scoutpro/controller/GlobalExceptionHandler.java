package xyz.riocode.scoutpro.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import xyz.riocode.scoutpro.error.AppError;
import xyz.riocode.scoutpro.exception.DuplicatePlayerException;
import xyz.riocode.scoutpro.exception.ParseException;
import xyz.riocode.scoutpro.exception.PlayerNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author Nikola Cvetkovic
 */

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
    
    private static final Logger LOG = LogManager.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<AppError> handlePlayerNotFound(HttpServletRequest req, PlayerNotFoundException ex){
        LOG.error(ex.getLocalizedMessage(), ex);
        AppError err = new AppError(ThreadContext.pop(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, 
                                    ex.getLocalizedMessage(), req.getRequestURI());
        
        return buildResponseEntity(err);
    }
    
    @ExceptionHandler(DuplicatePlayerException.class)
    public ResponseEntity<AppError> handleDuplicatePlayer(HttpServletRequest req, DuplicatePlayerException ex){
        LOG.error(ex.getLocalizedMessage(), ex);
        AppError err = new AppError(ThreadContext.pop(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, 
                                    ex.getLocalizedMessage(), req.getRequestURI());
        
        return buildResponseEntity(err);
    }
    
    @ExceptionHandler(ParseException.class)
    public ResponseEntity<AppError> handleParseException(HttpServletRequest req, ParseException ex){
        LOG.error(ex.getLocalizedMessage(), ex);
        AppError err = new AppError(ThreadContext.pop(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, 
                                    ex.getLocalizedMessage(), req.getRequestURI());
        
        return buildResponseEntity(err);
    }
    
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<AppError> handleConstraintViolation(HttpServletRequest req, TransactionSystemException ex){
        LOG.error(ex.getLocalizedMessage(), ex);
        Throwable t = null;
        AppError err = null;
        if(ex.getCause() != null && ex.getCause().getCause() != null) {
            t = ex.getCause().getCause();
            if(t instanceof ConstraintViolationException){
                
                err = new AppError(ThreadContext.pop(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, 
                                    "Validation Error", req.getRequestURI());
                err.extractViolations(((ConstraintViolationException) t).getConstraintViolations());
            }
        } else {
            return additionalHandleConstraintViolation(req, ex);
        }
        return buildResponseEntity(err);
    }
    
    
    public ResponseEntity<AppError> additionalHandleConstraintViolation(HttpServletRequest req, TransactionSystemException ex){
        LOG.error(ex.getLocalizedMessage(), ex);
        Throwable t = null;
        AppError err = null;
        if(ex.getCause() != null) {
            t = ex.getCause();
            if(t instanceof ConstraintViolationException){
                
                err = new AppError(ThreadContext.pop(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, 
                                    "Validation Error", req.getRequestURI());
                err.extractViolations(((ConstraintViolationException) t).getConstraintViolations());
            }
        } else {
            err = new AppError(ThreadContext.pop(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, 
                                ex.getLocalizedMessage(), req.getRequestURI());
        }
        return buildResponseEntity(err);
    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<AppError> handleConstraintViolation(HttpServletRequest req, DataIntegrityViolationException ex){
        LOG.error(ex.getLocalizedMessage(), ex);
        Throwable t = null;
        AppError err = null;
        if(ex.getCause() != null) {
            t = ex.getCause();
                if(t.getLocalizedMessage().contains("Duplicate")){
                    err = new AppError(ThreadContext.pop(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, 
                                     "Player exists", req.getRequestURI());
                } else {
                    err = new AppError(ThreadContext.pop(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, 
                                     ex.getLocalizedMessage(), req.getRequestURI());
                }
        }
            return buildResponseEntity(err);
    }
    
    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOG.error(ex.getLocalizedMessage(), ex);
        AppError err = new AppError(ThreadContext.pop(), status.value(), status, "Bind Error", request.getContextPath());
        err.extractBindErrors(ex.getTarget().getClass().getSimpleName(), ex.getFieldErrors());
        
        return buildResponseEntity(err);
    }
    
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOG.error(ex.getLocalizedMessage(), ex);
        return new ResponseEntity<>(new AppError(ThreadContext.pop(), status.value(), status, ex.getLocalizedMessage(), request.getContextPath()), status);
    }
    
    private ResponseEntity buildResponseEntity(AppError err){
        return new ResponseEntity(err, err.getHttpStatus());
    }
    
    
        
}
