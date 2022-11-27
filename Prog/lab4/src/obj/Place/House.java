package obj.Place;

import obj.Alive.Human;
import obj.NotAlive.Door;
import obj.State;

public class House {
    private boolean globalLighSwitch = true;
    public static Room outside = new Room("снаружи", State.DEFAULT);
    public static Room hall = new Room("прихожая", State.LIGHT);
    public static Room kitchen = new Room("кухня", State.LIGHT);
    public static Room smallerRoom = new Room("комната Малыша", State.DARK);
    public static Room huliousRoom = new Room("комната Юлиоса", State.LIGHT);
    public static Room frBokRoom = new Room("комната фрекен Бок", State.LIGHT);

    public static Door outsideDoor = new Door("входная дверь", State.CLOSED);
    public static Door huliousDoor = new Door("дверь комнаты Юлиоса", State.CLOSED);
    public static Door frBokDoor = new Door("дверь комнаты фрекен Бок", State.CLOSED);

    public void globalLighSwitchOff(Human human){
        System.out.println(human.getName() + " выкручивает пробки на кухне ");
        hall.turnOffLight();
        kitchen.turnOffLight();
    }

    @Override
    public int hashCode() {
        int buff = 0;

        buff += outside.hashCode();
        buff += hall.hashCode();
        buff += kitchen.hashCode();
        buff += smallerRoom.hashCode();
        buff += huliousRoom.hashCode();
        buff += frBokRoom.hashCode();

        buff += outsideDoor.hashCode();
        buff += huliousDoor.hashCode();
        buff += frBokDoor.hashCode();

        return buff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        House check = (House) o;
        return hashCode() == check.hashCode();
    }

    @Override
    public String toString(){
        String buff = "";

        buff += outside.getName() + " ";
        buff += hall.getName() + " ";
        buff += kitchen.getName() + " ";
        buff += smallerRoom.getName() + " ";
        buff += huliousRoom.getName() + " ";
        buff += frBokRoom.getName() + " ";

        buff += outsideDoor.getName() + " ";
        buff += huliousDoor.getName() + " ";
        buff += frBokDoor.getName() + " ";

        return buff;
    }

}
