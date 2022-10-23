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
}
