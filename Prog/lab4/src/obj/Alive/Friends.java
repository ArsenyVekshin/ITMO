package obj.Alive;

import obj.NotAlive.Door;
import obj.Place.Room;
import obj.State;

public class Friends extends Human{

    public Friends(String name, State state, Room place) {
        super(name, state, place);
    }

    public void beginUnlocking(Door door){
        System.out.println(getName() + " просовывают проволоку в скважину замка " + door.getName());
    }

    public void finishUnlocking(Door door){
        System.out.println(getName() + " отрывают замок " + door.getName());
    }

}
