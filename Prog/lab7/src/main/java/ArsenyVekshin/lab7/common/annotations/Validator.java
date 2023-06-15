package ArsenyVekshin.lab7.common.annotations;

import java.util.ArrayList;

public class Validator {

    public static  <T> boolean validate(T value, ArrayList<Validatable> validators) throws IllegalArgumentException  {
        boolean result = true;
        for(Validatable validator : validators) {
            result = result && validator.validate(value);
        }
        return result;
    }

}
