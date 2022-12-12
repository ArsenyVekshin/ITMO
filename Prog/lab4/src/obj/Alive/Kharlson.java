package obj.Alive;

import obj.Components;
import obj.Entity;
import obj.NotAlive.Lighter;
import obj.NotAlive.Subject;
import obj.Place.House;
import obj.Place.Room;
import obj.State;

public class Kharlson extends Human implements HumanInterface{

    Lighter lighter = new Lighter("фонарик", State.OFF);
    Subject leash = new Subject("поводок", State.DEFAULT);
    public Kharlson(String name, State state, Room place) {
        super(name, state, place);
    }

    public void finallizeMummy(Entity mummy){
        System.out.println("    " + getName() + " прорезал большие глаза в полотенце и обвел их черным ободком");
        System.out.println("    " + getName() + " засовывает " + Components.TEETHS + " в " + Components.NAPKINS);
        System.out.println("    " + getName() + " закрепляет их при помощи " + Components.PATCH);
    }

    public void setCalm(Human victim){
        System.out.println(getName() + " успокаивает " + victim.getName());
        victim.setState(State.CALM);
    }

    public void pullLeash(Entity dog){
        System.out.println(getName() + " дергает за " + leash.getName());
        leash.effectOn(dog, State.FALLS);
    }

    public void switchOnLighter(Entity entity){
        System.out.println(getName() + " включает " + lighter.getName());
        lighter.effectOn(entity, State.LIGHT);
    }

    public void switchOffLighter(Entity entity){
        System.out.println(getName() + " выключает " + lighter.getName());
        lighter.effectOn(entity, State.DARK);
    }

    public void globalLighSwitchOff(House house) {
        house.globalLighSwitchOff(this);
    }
}
