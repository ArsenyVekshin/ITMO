package utils.validators;

/**
 * Class that validate that fields value isn't empty string
 */
public class StringNotNoneValidator implements Validatable{
    private static StringNotNoneValidator instance;

    public <T> boolean validate(T value) throws IllegalArgumentException {
        String s;
        try {
            s = (String) value;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Значение не является строкой");
        }
        if(value == null || s.isEmpty() || s.isBlank()) {
            throw new IllegalArgumentException("Значение не может быть пустой строкой");
        }
        return true;
    }

    @Override
    public Validatable getInstance() {
        if (instance == null) {
            instance = new StringNotNoneValidator();
        }
        return instance;
    }
}
