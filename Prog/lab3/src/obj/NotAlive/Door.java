package obj.NotAlive;

import obj.Alive.Human;
import obj.State;

public class Door extends Subject{
    public Door(String name, State state){
        super(name, state);
        this.setState(State.CLOSED);
    }

    public void open(Human human){
        setState(State.OPEN);
        System.out.println(human.getName() + " открывает " + getName());
    }

    public void close(Human human){
        setState(State.OPEN);
        System.out.println(human.getName() + " закрывает " + getName());
    }

}
