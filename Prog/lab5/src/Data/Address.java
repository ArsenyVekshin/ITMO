package Data;

import Exceptions.CSVOperator;
import Exceptions.InvalidValueEntered;
import Exceptions.NoneValueFromCSV;

public class Address implements CSVOperator {
    private String street; //Строка не может быть пустой, Поле не может быть null
    private String zipCode; //Поле может быть null

     public Address(String street, String zipCode) throws InvalidValueEntered {
         setStreet(street);
         setZipCode(zipCode);
     }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) throws InvalidValueEntered {
        if (zipCode == null) {
            this.zipCode = "default";
            throw new InvalidValueEntered(this.getClass().getName(), "zipCode");
        }
        else this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) throws InvalidValueEntered {
        if (street == null || street.length()==0) {
            this.street = "default";
            throw new InvalidValueEntered(this.getClass().getName(), "street");
        }
        else this.street = street;
    }

    @Override
    public void parseCSV(String input) throws NoneValueFromCSV {
        if(input == null || input.isEmpty() || !input.contains(";")) {
            throw new NoneValueFromCSV(this.getClass().getName(), input);
        }
        else{
            String[] data = input.split(";");
            this.street = data[0];
            this.zipCode = data[1];
        }
    }
    @Override
    public String generateCSV(){
        return street + ";" + zipCode;
    }

}
