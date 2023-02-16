package collection.exceptions;

public interface CSVOperator {
    public void parseCSV(String input) throws NoneValueFromCSV, InvalidValueEntered;
    public String generateCSV();

}
