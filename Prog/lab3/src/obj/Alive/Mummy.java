package obj.Alive;

import obj.Place.Room;
import obj.State;

public class Mummy extends Human {

    public Mummy(String name, State state, Room place) {
        super(name, state, place);
    }

    public void scare_smb(Human victim){
        victim.setState(State.SCARED);
        System.out.println(getName() + " приводит в ужас " + victim.getName());
    }


}