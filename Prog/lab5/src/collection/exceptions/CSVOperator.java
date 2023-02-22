package collection.exceptions;

public interface CSVOperator {
    default void parseCSV(String input) throws NoneValueFromCSV, InvalidValueEntered {
        return;
    }

    default String generateCSV(){
        return "";
    }

}
