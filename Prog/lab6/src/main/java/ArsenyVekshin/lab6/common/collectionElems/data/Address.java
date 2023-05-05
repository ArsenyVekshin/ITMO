package ArsenyVekshin.lab6.common.collectionElems.data;

import ArsenyVekshin.lab6.common.collectionElems.CSVOperator;
import ArsenyVekshin.lab6.common.collectionElems.exceptions.*;
import ArsenyVekshin.lab6.server.utils.validators.NotNull;
import ArsenyVekshin.lab6.server.utils.validators.StringNotNone;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Supplier;

import static ArsenyVekshin.lab6.common.tools.Comparators.compareFields;

public class Address extends Entity implements Cloneable, Comparable, CSVOperator, Serializable {

    @NotNull
    @StringNotNone
    private String street; //Строка не может быть пустой, Поле не может быть null

    private String zipCode; //Поле может быть null

    public Address(){}
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
                ", zipCode=" + zipCode + ");";
    }

    @Override
    public String generateCSV() {
        return street + ", " + zipCode ;
    }

    @Override
    public void init(HashMap<String, Object> values) {
        this.street = (String) values.get("street");
        this.zipCode = (String) values.get("zipCode");
    }

    @Override
    public HashMap<String, Object> getValues() {
        HashMap<String, Object> values = new HashMap<>();
        values.put("street", street);
        values.put("zipCode", zipCode);
        return values;
    }


    @Override
    public Supplier<Entity> getConstructorReference() {
        return Address::new;
    }

    @Override
    public Address clone() throws CloneNotSupportedException {
        return (Address) super.clone();
    }

    @Override
    public int compareTo(Object o) {
        if (o == null || getClass() != o.getClass()) return 0;
        return compareFields(((Address)o).getStreet(), getStreet()) +
                compareFields(((Address)o).getZipCode(), getZipCode());
    }


}
