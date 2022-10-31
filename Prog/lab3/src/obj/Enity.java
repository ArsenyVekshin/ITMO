package obj;

import obj.Alive.Human;

public abstract class Enity implements EnityInterface{
    private final String name;
    private State state;


    public Enity(String name, State state) {
        this.name = name;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setState(State state) {
        this.state = state;
        System.out.println(name + " " + state.getReplic());
    }

    public State getState() {
        return state;
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

        Enity check = (Enity) o;
        return hashCode() == check.hashCode();
    }

    @Override
    public String toString(){
        return getName() + " " + getState();
    }

}
