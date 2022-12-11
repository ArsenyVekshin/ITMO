package perif;

import exception.DontMoveToMummyException;
import exception.WrongCreatorException;
import obj.Alive.*;
import obj.Components;
import obj.Entity;
import obj.Place.*;
import obj.Sound.*;
import obj.State;

public class Story {

    Creator creator = new Creator(){
        final Components[] allComp = Components.values();
        @Override
        public void createSmth(Human human, Entity obj) throws WrongCreatorException {
            if(human.getClass()!= Kharlson.class) throw new WrongCreatorException("Он не может создать мумию");
            System.out.print(human.getName() + " создает " + obj.getName() + " из: \n    ");
            for (byte i=0; i<allComp.length; i++)
                System.out.print(allComp[i].getReplic() + ", ");
            System.out.println();
        }
    };

    House house = new House();

    Friends friends = new Friends("Филле и Рулле", State.DEFAULT, house.outside);

    Human hulious = new Human("Юлиус", State.DEFAULT, house.huliousRoom);
    Human dog = new Human("Мамочка", State.DEFAULT, house.smallerRoom);
    Smaller smaller = new Smaller("Малыш", State.DEFAULT, house.smallerRoom);
    Kharlson kharlson = new Kharlson("Карлсон", State.DEFAULT, house.smallerRoom);
    FrBok frBok = new FrBok("фрэкен Бок", State.DEFAULT, house.frBokRoom);
    Mummy mummy = new Mummy("мумия", State.DEFAULT, house.smallerRoom);

    Sound soundSteps = new Sound("шаги", State.CONFUSED);
    Sound soundRumble = new Sound("грохот", State.SCARED);
    Sound soundScreams = new Sound("крики", State.SCARED);
    Sound soundCreak = new Sound("полязгивание и скрежет", State.AWAKEN);
    Sound soundCalm = new Sound("абсолютная тишина", State.DEFAULT);
    Sound soundSnoring = new Sound("храп", State.DEFAULT);

    public void run(){
        defaultStory();
    }

    public void defaultStory(){

        System.out.println("--------------------------------------------------------");

        kharlson.said("Проказничать лучше в темноте!");
        kharlson.globalLighSwitchOff(house);
        System.out.println();

        try {
            creator.createSmth(kharlson, mummy);
        }
        catch (WrongCreatorException e){
            System.out.println(e.getMessage());
        }
        //creator.createSmth(kharlson, mummy);
        kharlson.finallizeMummy(mummy);
        System.out.println();

        mummy.scare_smb(smaller);
        smaller.setState(State.CALM);
        smaller.dragSmb(mummy, house.hall);
        System.out.println();

        friends.beginUnlocking(House.outsideDoor);
        soundCreak.heardsBy(kharlson, smaller);
        kharlson.moveToRoom(house.hallTable);
        smaller.moveToRoom(house.hallTable);
        smaller.setState(State.SLEEP);
        System.out.println();

        friends.finishUnlocking(House.outsideDoor);
        house.outsideDoor.open(friends);
        friends.moveToRoom(house.hallCoat);
        smaller.setState(State.AWAKEN);
        smaller.setState(State.SCARED);
        kharlson.setCalm(smaller);
        System.out.println();

        // Старый код, 99% нетронуто
        mummy.moveToRoom(house.hall);
        soundSteps.heardsBy(smaller);
        soundRumble.heardsBy(smaller);
        soundScreams.heardsBy(smaller);
        mummy.setState(State.LEAN);
        mummy.setState(State.SCALLEDTEETHS);
        System.out.println();

        kharlson.switchOnLighter(mummy);
        kharlson.switchOffLighter(mummy);
        soundScreams.heardsBy(smaller, kharlson);
        house.huliousDoor.open(hulious);
        hulious.moveToRoom(house.hall);
        house.frBokDoor.open(frBok);
        frBok.moveToRoom(house.hall);
        System.out.println();

        soundSteps.heardsBy(smaller);
        kharlson.pullLeash(dog);
        frBok.switchOn(house.hall);
        frBok.switchOn(house.hall);
        frBok.switchOn(house.hall);
        frBok.setState(State.CONFUSED);
        hulious.setState(State.CONFUSED);
        System.out.println();
        //Конец старого кода

        frBok.said("Это наверняка гром, я не могу ошибаться");
        hulious.said("шкашошные шушештва");
        smaller.think("Он ведь остался без зубов");
        smaller.think("Где они? Убежали?", friends);
        smaller.setState(State.SCARED);
        try {
            smaller.moveNearTo(kharlson);
        }
        catch (DontMoveToMummyException e){
            System.out.println(e.getMessage());
        }
        System.out.println();

        frBok.moveToRoom(house.frBokRoom);
        hulious.moveToRoom(house.huliousRoom);
        soundCalm.exist();
        smaller.think("прошла уже целая вечность");
        soundSnoring.exist();
        frBok.setState(State.SLEEP);
        hulious.setState(State.SLEEP);

    }

}
