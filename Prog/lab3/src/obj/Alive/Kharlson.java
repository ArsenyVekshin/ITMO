package obj.Alive;

import obj.Enity;
import obj.NotAlive.Lighter;
import obj.NotAlive.Subject;
import obj.Place.House;
import obj.Place.Room;
import obj.State;

public class Kharlson extends Human{

    Lighter lighter = new Lighter("фонарик", State.OFF);
    Subject leash = new Subject("поводок", State.DEFAULT);
    public Kharlson(String name, State state, Room place) {
        super(name, state, place);
    }

    public void pullLeash(Enity dog){
        System.out.println(getName() + " дергает за " + leash.getName());
        leash.effectOn(dog, State.FALLS);
    }

    public void switchOnLighter(Enity enity){
        System.out.println(getName() + " включает " + lighter.getName());
        lighter.effectOn(enity, State.LIGHT);
    }

    public void switchOffLighter(Enity enity){
        System.out.println(getName() + " выключает " + lighter.getName());
        lighter.effectOn(enity, State.DARK);
    }

    public void globalLighSwitchOff(House house) {
        house.globalLighSwitchOff(this);
    }
}
