package xyz.riocode.scoutpro.error;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nikola Cvetkovic
 */

public class BindingSubAppErr extends SubAppErr{
    
    private String target;
    private List<Field> fields;
    
    public BindingSubAppErr() {
    }

    public BindingSubAppErr(String target) {
        this.target = target;
    }
    
    public void addField(Field f){
        if(this.fields == null) this.fields = new ArrayList<>();
        this.fields.add(f);
    }
    
    public Field createFieldError(String fieldName, String rejectedValue, String message){
        return new Field(fieldName, rejectedValue, message);
    }
    
    class Field{
        
        String fieldName;
        String rejectedValue;
        String message;

        public Field(String fieldName, String rejectedValue, String message) {
            this.fieldName = fieldName;
            this.rejectedValue = rejectedValue;
            this.message = message;
        }
        
    }
}
