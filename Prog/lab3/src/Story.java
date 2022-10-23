import obj.Alive.FrBok;
import obj.Alive.Human;
import obj.Alive.Kharlson;
import obj.Alive.Mummy;
import obj.Place.House;
import obj.Sound.Sound;
import obj.State;

public class Story {
    House house = new House();

    Human hulious = new Human("Юлиус", State.DEFAULT, house.huliousRoom);
    Human dog = new Human("Мамочка", State.DEFAULT, house.smallerRoom);
    Human smaller = new Human("Малыш", State.DEFAULT, house.smallerRoom);
    Kharlson kharlson = new Kharlson("Карлсон", State.DEFAULT, house.smallerRoom);
    FrBok frBok = new FrBok("фрэкен Бок", State.DEFAULT, house.frBokRoom);
    Mummy mummy = new Mummy("мумия", State.DEFAULT, house.outside);

    Sound soundSteps = new Sound("шаги", State.CONFUSED);
    Sound soundRumble = new Sound("грохот", State.SCARED);
    Sound soundScreams = new Sound("крики", State.SCARED);


    public void run(){
        defaultStory();
    }

    public void defaultStory(){
        kharlson.said("Проказничать лучше в темноте!");
        kharlson.globalLighSwitchOff(house);

        house.outsideDoor.open(mummy);
        mummy.moveToRoom(house.hall);

        soundSteps.heardsBy(smaller);
        soundRumble.heardsBy(smaller);
        soundScreams.heardsBy(smaller);

        mummy.setState(State.LEAN);
        mummy.setState(State.SCALLEDTEETHS);
        kharlson.switchOnLighter(mummy);
        kharlson.switchOffLighter(mummy);

        soundScreams.heardsBy(smaller, kharlson);
        house.huliousDoor.open(hulious);
        hulious.moveToRoom(house.hall);
        house.frBokDoor.open(frBok);
        frBok.moveToRoom(house.hall);

        soundSteps.heardsBy(smaller);
        kharlson.pullLeash(dog);
        frBok.switchOn(house.hall);
        frBok.switchOn(house.hall);
        frBok.switchOn(house.hall);

        frBok.setState(State.CONFUSED);
        hulious.setState(State.CONFUSED);
    }

}
