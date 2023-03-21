package ArsenyVekshin.lab5.collection.data;

import ArsenyVekshin.lab5.collection.CSVOperator;
import ArsenyVekshin.lab5.collection.exceptions.*;
import ArsenyVekshin.lab5.utils.validators.NotNull;
import ArsenyVekshin.lab5.utils.validators.StringNotNone;

import java.util.Objects;

public class Address implements CSVOperator {

    @NotNull
    @StringNotNone
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
public int hashCode() {
    return Objects.hash(street, zipCode);
}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address check = (Address) o;
        return hashCode() == check.hashCode();
    }

    @Override
    public String toString(){
        return "Address(" +
                "street=" + street +
                ", name=" + zipCode + ");";
    }

    @Override
    public String generateCSV() {
        return street + ", " + zipCode ;
    }
}
