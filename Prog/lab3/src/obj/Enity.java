package obj;

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
}
