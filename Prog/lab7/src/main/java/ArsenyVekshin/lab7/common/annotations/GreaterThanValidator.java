package ArsenyVekshin.lab7.common.annotations;


import ArsenyVekshin.lab7.common.builder.Converter;

/**
 * Class that validate that field grater or equal than certain value
 */
public class GreaterThanValidator implements ValidatableWithArg {
    private String value;
    public GreaterThanValidator(String value) {
        this.value = value;
    }
    public GreaterThanValidator() {
        this.value = "0";
    }

    public GreaterThanValidator getInstance(String value) {
        return new GreaterThanValidator(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U> boolean validate(U value) throws IllegalArgumentException {
        U tmp = (U) Converter.convert(value.getClass(), this.value);

        if(((Comparable<U>)value).compareTo(tmp) <= 0) {
            throw new IllegalArgumentException("Значение должно быть больше " + this.value + " введено " + value);
        }
        return true;
    }

    @Override
    public Validatable getInstance() {
        return new GreaterThanValidator("0");
    }
}