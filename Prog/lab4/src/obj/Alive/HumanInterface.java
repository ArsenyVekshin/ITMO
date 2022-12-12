package obj.Alive;

import obj.Entity;
import obj.Place.Room;
import obj.State;

public interface HumanInterface {
    public void said(String mes);
    public void think(String mes);
    public void think(String mes, Entity... obj);

    public void moveToRoom(Room destination);
    public void switchOff(Room place);
    public void switchOn(Room place);

    public int hashCode();
    public boolean equals(Object o);
    public String toString();
}
