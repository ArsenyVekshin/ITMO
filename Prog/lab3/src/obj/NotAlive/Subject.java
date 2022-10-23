package obj.NotAlive;

import obj.Enity;
import obj.State;

public class Subject extends Enity {

    public Subject(String name, State state) {
        super(name, state);
    }

    public void effectOn(Enity victim, State stat){
        System.out.println(getName() + " накладывает эффект " + stat.getReplic() + " на " + victim.getName());
        victim.setState(stat);
    }
}
