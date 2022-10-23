package obj.Place;

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
}
