package collection.data;

import collection.exceptions.CSVOperator;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Coordinates implements Cloneable, CSVOperator {
    private float x;
    private float y;

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

    public Coordinates clone() throws CloneNotSupportedException {
        return (Coordinates) super.clone();
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
}
