package obj.NotAlive;

import obj.Entity;
import obj.State;

public class Lighter extends Subject{
    public Lighter(String name, State state){
        super(name, state);
        this.setState(State.OFF);
    }

    public void switchOn(Entity entity){
        setState(State.ON);
        System.out.println(entity.getName() + " включает " + getName());
    }

    public void switchOff(Entity entity){
        setState(State.OFF);
        System.out.println(entity.getName() + " выключает " + getName());
    }

}
