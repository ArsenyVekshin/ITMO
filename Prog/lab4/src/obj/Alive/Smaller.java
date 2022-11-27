package obj.Alive;

import obj.Entity;
import obj.Place.Room;
import obj.State;

public class Smaller extends Human{

    public Smaller(String name, State state, Room place) {
        super(name, state, place);
    }

    public void dragSmb(Human victim, Room destination ){
        System.out.println(getName() + " тащит " + victim.getName() + " в " + destination.getName());
        moveToRoom(destination);
        victim.moveToRoom(destination);
    }

    public void moveNearTo(Entity victim){
        System.out.println(getName() + " пододвинулся ближе к " + victim.getName());
    }
}
