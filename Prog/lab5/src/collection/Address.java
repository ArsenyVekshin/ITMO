package collection;

import collection.exceptions.InvalidValueEntered;
import com.opencsv.bean.CsvBindByName;

import static collection.Storage.key2obj;

public class Address{

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

//    public void parseCSV(String input) throws NoneValueFromCSV, InvalidValueEntered{
//         if(input == null || input.isEmpty() || input.indexOf(";")==-1)
//    }

}
