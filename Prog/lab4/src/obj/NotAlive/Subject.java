package obj.NotAlive;

import obj.Alive.Human;
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


    @Override
    public int hashCode() {
        int buff = 0;

        for (byte i=0; i<getName().length(); i++) buff += getName().charAt(i);
        for (byte i=0; i<getState().toString().length(); i++) buff += getState().toString().charAt(i);

        return buff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject check = (Subject) o;
        return hashCode() == check.hashCode();
    }

    @Override
    public String toString(){
        return getName() + " " + getState();
    }
}
