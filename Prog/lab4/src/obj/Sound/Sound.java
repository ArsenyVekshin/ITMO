package obj.Sound;

import obj.Alive.Human;
import obj.Entity;
import obj.State;

public class Sound extends Entity {

    public Sound(String name, State state) {
        super(name, state);
    }

    public void exist(){
        System.out.println(getName() + " послышались");
    }

    public void heardsBy(Human... humans){
        String out = "";
        for (Human human : humans) {
            out += human.getName() + " ";
        }
        System.out.println(out + "слышат " + getName());

        if(getState()!=State.DEFAULT) {
            for (Human human : humans) {
                human.setState(getState());
            }
        }
    }
}
