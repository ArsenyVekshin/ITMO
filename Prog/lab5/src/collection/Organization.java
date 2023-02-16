package collection;

import collection.exceptions.InvalidValueEntered;
import com.opencsv.bean.CsvBindByName;
public class Organization{
    @CsvBindByName(column = "manufacturer id")
    private long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @CsvBindByName(column = "manufacturer name")
    private String name; //Поле не может быть null, Строка не может быть пустой
    @CsvBindByName(column = "manufacturer annual turnover")
    private double annualTurnover; //Значение поля должно быть больше 0
    @CsvBindByName(column = "manufacturer type")
    private OrganizationType type; //Поле не может быть null
    @CsvBindByName(column = "manufacturer address")
    private Address postalAddress; //Поле может быть null


    public Organization(long id, String name, double annualTurnover, OrganizationType type, Address postalAddress){
        this.id = id;
        try {
            setName(name);
            setAnnualTurnover(annualTurnover);
            setType(type);
            setPostalAddress(postalAddress);
        }
        catch (InvalidValueEntered e){
            System.out.println(e.getMessage());
        }
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidValueEntered {
        if (name == null || name.length()==0) {
            this.name = "default";
            throw new InvalidValueEntered(this.getClass().getName(), "name");
        }
        else this.name = name;
    }


    public double getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(double annualTurnover) throws InvalidValueEntered {
        if (annualTurnover<=0) {
            this.annualTurnover = 1;
            throw new InvalidValueEntered(this.getClass().getName(), "annualTurnover");
        }
        else this.annualTurnover = annualTurnover;
    }

    public OrganizationType getType() {
        return type;
    }

    public void setType(OrganizationType type) throws InvalidValueEntered {
        if (type == null) {
            this.type = OrganizationType.DEFAULT;
            throw new InvalidValueEntered(this.getClass().getName(), "type");
        }
        else this.type = type;
    }

    public Address getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(Address postalAddress) throws InvalidValueEntered {
        if (postalAddress == null) {
            this.postalAddress = new Address("default", "default");
            throw new InvalidValueEntered(this.getClass().getName(), "postalAddress");
        }
        else this.postalAddress = postalAddress;
    }


}