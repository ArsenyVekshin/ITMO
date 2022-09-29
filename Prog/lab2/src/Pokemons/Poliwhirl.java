package Pokemons;

import Moves.Poliwhirl.*;

public class Poliwhirl extends Poliwag {
    public Poliwhirl(String name, int level) {
        super(name, level);
        setStats(40, 50, 40, 40, 40, 90);
        addMove(new WaterGun());
    }
}
