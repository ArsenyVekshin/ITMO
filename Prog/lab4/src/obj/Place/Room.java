package obj.Place;

import obj.Alive.Human;
import obj.Enity;
import obj.State;

public class Room extends Enity {
    public Room(String name, State state) {
        super(name, state);
    }


    public void turnOnLight(){
        System.out.println("в " + getName() + " включается свет");
        setState(State.LIGHT);
    }

    public void turnOffLight(){
        System.out.println("в " + getName() + " выключается свет");
        setState(State.DARK);
    }

    @Override
    public int hashCode() {
        int buff = 0;

        for (byte i=0; i<getName().length(); i++) buff += getName().charAt(i);
        for (byte i=0; i<getState().toString().length(); i++) buff += getState().toString().charAt(i);

        return buff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room check = (Room) o;
        return hashCode() == check.hashCode();
    }

    @Override
    public String toString(){
        return getName() + " " + getState();
    }

}
