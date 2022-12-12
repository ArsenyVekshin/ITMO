package obj.Alive;

import obj.Place.Room;
import obj.State;

public class FrBok extends Human implements HumanInterface{
    public FrBok(String name, State state, Room place) {
        super(name, state, place);
    }

    @Override
    public void switchOn(Room place){
        System.out.println(getName() + " использует выключатель(включить) в " + place.getName() + " - ошибка");
    }
}