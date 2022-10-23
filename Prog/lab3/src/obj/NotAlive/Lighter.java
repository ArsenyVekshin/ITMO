package obj.NotAlive;

import obj.Enity;
import obj.State;

public class Lighter extends Subject{
    public Lighter(String name, State state){
        super(name, state);
        this.setState(State.OFF);
    }

    public void switchOn(Enity enity){
        setState(State.ON);
        System.out.println(enity.getName() + " включает " + getName());
    }

    public void switchOff(Enity enity){
        setState(State.OFF);
        System.out.println(enity.getName() + " выключает " + getName());
    }

}
