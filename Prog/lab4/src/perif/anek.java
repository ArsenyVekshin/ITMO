package perif;

import java.util.Random;

import static java.lang.Math.random;
/*
АХТУНГ!!
Все приведенное ниже вставленно шутки ради
А также для того чтобы вставить в прогу то что должно быть в тз, но чему не нашлось места в коде
 */
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
