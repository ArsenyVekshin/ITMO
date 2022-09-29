import Pokemons.*;
import ru.ifmo.se.pokemon.Battle;

//var 765567
public class Lab2 {
    public static void main(String[] args){
        Battle b = new Battle();

        Scarmory p1 = new Scarmory("1", 1);
        Ponyta p2 = new Ponyta("2", 1);
        Rapidash p3 = new Rapidash("3", 1);
        Poliwag p4 = new Poliwag("4", 1);
        Poliwhirl p5 = new Poliwhirl("5", 1);
        Poliwrath p6 = new Poliwrath("6", 1);

        b.addAlly(p1);
        b.addAlly(p3);
        b.addAlly(p5);

        b.addFoe(p2);
        b.addFoe(p4);
        b.addFoe(p6);

        b.go();
    }
}
