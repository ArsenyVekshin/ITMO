package Pokemons;

import Moves.Poliwrath.*;

public class Poliwrath extends Poliwhirl {
    public Poliwrath(String name, int level) {
        super(name, level);
        setStats(90, 95, 95, 70, 90, 70);
        addMove(new DynamicPunch());
    }
}
