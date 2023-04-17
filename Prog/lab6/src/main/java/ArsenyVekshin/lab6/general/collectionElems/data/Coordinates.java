package ArsenyVekshin.lab6.general.collectionElems.data;

import ArsenyVekshin.lab6.general.collectionElems.CSVOperator;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.Supplier;

import static ArsenyVekshin.lab6.server.tools.Comparators.compareFields;

public class Coordinates extends Entity implements Cloneable, Comparable, CSVOperator {
    private float x;
    private float y;

    public Coordinates(){};
    public Coordinates(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public void setX(float x) {
        this.x = x;
    }
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinates check = (Coordinates) o;
        return hashCode() == check.hashCode();
    }

    @Override
    public String toString(){
        return "Coordinates(" +
                "x=" + x +
                ", y=" + y + ");";
    }

    @Override
    public String generateCSV() {
        return x + ", " + y;
    }

    @Override
    public void init(HashMap<String, Object> values) {
        this.x = (float) values.get("x");
        this.y = (float) values.get("y");
    }

    @Override
    public HashMap<String, Object> getValues() {
        HashMap<String, Object> values = new HashMap<>();
        values.put("x", x);
        values.put("y", y);
        return values;
    }

    public Supplier<Entity> getConstructorReference() {
        return Coordinates::new;
    }

    @Override
    public Coordinates clone() throws CloneNotSupportedException {
        return (Coordinates) super.clone();
    }

    @Override
    public int compareTo(Object o) {
        if (o == null || getClass() != o.getClass()) return 0;
        return compareFields(((Coordinates)o).getX(), getX()) +
                compareFields(((Coordinates)o).getY(), getY());
    }
}
