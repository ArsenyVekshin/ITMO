package Data;
import Exceptions.InvalidValueEntered;

public class Location {
    private float x; //Не может быть null, тк примитив
    private double y; //Поле не может быть null, тк примитив
    private float z; //Поле не может быть null, тк примитив
    private String name; //Поле не может быть null

    public Location(float x, double y, float z, String name){
        this.x = x;
        this.y = y;
        this.z = z;
        try {
            setName(name);
        }
        catch (InvalidValueEntered e){
            System.out.println(e.getMessage());
        }
    }

    public double calcDistance(Location to){
        return Math.sqrt(Math.pow(to.getX()-x,2) + Math.pow(to.getY()-y,2) + Math.pow(to.getZ()-z,2));
    }
    public String getName(){
        return name;
    }

    public void setName(String name) throws InvalidValueEntered{
        if (name == null) {
            this.name = "default";
            throw new InvalidValueEntered(this.getClass().getName(), "name");
        }
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }
}

