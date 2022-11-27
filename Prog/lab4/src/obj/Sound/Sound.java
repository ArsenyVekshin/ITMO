package obj.Sound;

import obj.Alive.Human;
import obj.Enity;
import obj.State;

public class Sound extends Enity {

    public Sound(String name, State state) {
        super(name, state);
    }

    public void heardsBy(Human... humans){
        String out = "";
        for (Human human : humans) {
            out += human.getName() + " ";
        }
        System.out.println(out + "слышат " + getName());

        for (Human human : humans) {
            human.setState(getState());
        }
    }
}
