package Data;

import Exceptions.InvalidValueEntered;


public class Person {
    private int id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location from; //Поле может быть null
    private Location to; //Поле может быть null
    private double distance; //Значение поля должно быть больше 1
    
    public Person(int id, String name, Coordinates coordinates, Location from, Location to, double distance){
        this.id = id; //генерируется вне объекта из глобального счетчика
        creationDate = java.time.LocalDateTime.now();
        try {
            setName(name);
            setCoordinates(coordinates);
            setFrom(from);
            setTo(to);
            setDistance(distance);
        }
        catch (InvalidValueEntered e){
            System.out.println(e.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidValueEntered {
        if (name == null) {
            this.name = "default";
            throw new InvalidValueEntered(this.getClass().getName(), "name");
        }
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) throws InvalidValueEntered {
        if (coordinates == null) {
            this.coordinates = new Coordinates(0,0);
            throw new InvalidValueEntered(this.getClass().getName(), "coordinates");
        }
    }

    public Location getFrom() {
        return from;
    }

    public void setFrom(Location from) throws InvalidValueEntered{
        if (from == null) {
            this.from = new Location(0,0, 0, "default");
            throw new InvalidValueEntered(this.getClass().getName(), "from");
        }
    }

    public Location getTo() {
        return to;
    }

    public void setTo(Location to) throws InvalidValueEntered{
        if (to == null) {
            this.to = new Location(0,0, 0, "default");
            throw new InvalidValueEntered(this.getClass().getName(), "to");
        }
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) throws InvalidValueEntered{
        if (distance <= 1) {
            this.distance = from.calcDistance(to);
            throw new InvalidValueEntered(this.getClass().getName(), "distance");
        }
    }
}


