package obj.Alive;

import obj.Enity;
import obj.Place.Room;
import obj.State;

public class Human extends Enity {
    public Room place;
    public Human(String name, State state, Room place) {
        super(name, state);
        this.place = place;
    }

    public void said(String mes){
        System.out.println(getName() + " говорит: " + mes);
    }

    public void moveToRoom(Room destination){
        System.out.println(getName() + " перемещается из " + place.getName() + " в " + destination.getName());
        place = destination;
    }
    public void switchOff(Room place){
        System.out.println(getName() + " использует выключатель(выключить) в " + place.getName());
        place.turnOffLight();
    }
    public void switchOn(Room place){
        System.out.println(getName() + " использует выключатель(включить) в " + place.getName());
        place.turnOnLight();
    }

    @Override
    public int hashCode() {
        int buff = 0;

        for (byte i=0; i<getName().length(); i++) buff += getName().charAt(i);
        for (byte i=0; i<place.getName().length(); i++) buff += place.getName().charAt(i);
        for (byte i=0; i<getState().toString().length(); i++) buff += getState().toString().charAt(i);

        return buff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Human check = (Human) o;
        return hashCode() == check.hashCode();
    }

    @Override
    public String toString(){
        return getName() + " " + getState() + " " + place.toString();
    }
}
