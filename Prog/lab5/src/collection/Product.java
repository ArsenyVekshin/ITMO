package collection;

import collection.exceptions.InvalidValueEntered;
import com.opencsv.bean.CsvBindByName;

import java.time.LocalDate;


public class Product {
    @CsvBindByName(column = "id")
    private int id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @CsvBindByName(column = "name")
    private String name; //Поле не может быть null, Строка не может быть пустой
    @CsvBindByName(column = "coordinates")
    private Coordinates coordinates; //Поле не может быть null
    @CsvBindByName(column = "creation date")
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @CsvBindByName(column = "price")
    private float price; //Значение поля должно быть больше 0
    @CsvBindByName(column = "unit of measure")
    private UnitOfMeasure unitOfMeasure; //Поле может быть null
    @CsvBindByName(column = "manufacturer")
    private Organization manufacturer; //Поле может быть null


    public Product(int id, String name, Coordinates coordinates, UnitOfMeasure unitOfMeasure, Organization manufacturer){
        this.id = id; //генерируется вне объекта из глобального счетчика
        creationDate = LocalDate.from(java.time.LocalDateTime.now());
        try {
            setName(name);
            setCoordinates(coordinates);
            setPrice(price);
            setUnitOfMeasure(unitOfMeasure);
            setManufacturer(manufacturer);
        }
        catch (InvalidValueEntered e){
            System.out.println(e.getMessage());
        }
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

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) throws InvalidValueEntered {
        if (coordinates == null) {
            this.coordinates = new Coordinates(0,0);
            throw new InvalidValueEntered(this.getClass().getName(), "coordinates");
        }
        else this.coordinates = coordinates;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) throws InvalidValueEntered {
        if (price <=0) {
            this.price = 1;
            throw new InvalidValueEntered(this.getClass().getName(), "price");
        }
        else this.price = price;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) throws InvalidValueEntered {
        if (unitOfMeasure == null) {
            this.unitOfMeasure = UnitOfMeasure.DEFAULT;
            throw new InvalidValueEntered(this.getClass().getName(), "unitOfMeasure");
        }
        else this.unitOfMeasure = unitOfMeasure;
    }

    public Organization getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Organization manufacturer) throws InvalidValueEntered {
        if (manufacturer == null) throw new InvalidValueEntered(this.getClass().getName(), "manufacter");
        else this.manufacturer = manufacturer;
    }

    public int getId() {
        return id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
}


