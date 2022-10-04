package Places;

import People.Human;

public class Home {
    protected static boolean globalLightSwitch = true;
    Place lobby = new Place("тамбур");
    Place UnderTable = new Place("под столом");
    Place UlliusRoom = new Place("комната дяди Юлиуса");
    Place FBRoom = new Place("комната Фреккен Бок");
    Place Kitchen = new Place("кухня");

    void globalLightOff(Human human){
        if (human == null || human.where()!=Kitchen.getName()) return;
        globalLightSwitch=false;
        System.out.println(human.getName() + " выкручивает пробки в щитке");
    }
    void globalLightOn(Human human){
        if (human == null || human.where()!=Kitchen.getName()) return;
        globalLightSwitch=true;
        System.out.println(human.getName() + " вкручивает пробки в щитке");
    }
}
