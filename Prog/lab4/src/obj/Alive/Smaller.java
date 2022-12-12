package obj.Alive;

import exception.DontMoveToMummyException;
import obj.Entity;
import obj.Place.Room;
import obj.State;

public class Smaller extends Human implements HumanInterface{

    public Smaller(String name, State state, Room place) {
        super(name, state, place);
    }

    public void dragSmb(Human victim, Room destination ){
        System.out.println(getName() + " тащит " + victim.getName() + " в " + destination.getName());
        moveToRoom(destination);
        victim.moveToRoom(destination);
    }

    public void moveNearTo(Entity victim) throws DontMoveToMummyException {
        if(victim.getClass() == Mummy.class) throw new DontMoveToMummyException("Зачем он прислоняется к мумии?");
        System.out.println(getName() + " пододвинулся ближе к " + victim.getName());
    }
}
