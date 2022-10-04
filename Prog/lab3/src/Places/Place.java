package Places;

import People.Human;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Place {

    private String name = "default";
    private ArrayList<Human> inside = new ArrayList<Human>() ;
    private boolean lightened = false;


    Place(String name, Human... arg) {
        this.name = name;
        if (arg == null) return;
        Collections.addAll(inside, arg);
    }

    public String whoInside() {
        if(inside == null) return "nobody";

        String out="";
        for (Human human : inside) {
            out += human.getName() + " ";
        }
        return out;

    }

    public void enter(Human... arg){
        if (arg == null) return;
        for (Human human : arg) {
            inside.add(human);
            human.setPlace(name);
            System.out.print(human.getName() + " ");
        }
        System.out.println("зашел(-ли) в " + name);
    }

    public void exit(Human... arg){
        if (arg == null) return;
        for (Human human : arg) {
            inside.remove(human);
            System.out.print(human.getName() + " ");
        }
        System.out.println("вышел(-ли) из " + name);
    }
    public void turnOnLight(Human arg){
        if(Home.globalLightSwitch){
            System.out.println(arg.getName() + " включает свет в " + name);
            lightened = true;
        }
        else{
            System.out.println(arg.getName() + " не может включить свет в " + name);
            lightened = false;
        }
    }

    public void turnOffLight(Human arg){
        if (!Home.globalLightSwitch) {
            System.out.println(arg.getName() + " выключает свет в " + name);
        }
        lightened = false;
    }

    public String getName() {
        return name;
    }

}
