package perif;

import java.util.Random;

import static java.lang.Math.random;

public class anek {
    public anek(){}

    private static class result{
        static String generate(){
            if((float) random()<=0.8) return "насрано";
            else return "с пивом потянет";
        }
    }

    void run(){
        System.out.println("Открываю uml-диаграмму проекта а там " + result.generate());
    }
}
