package Pokemons;

import Moves.Ponyta.Agility;
import Moves.Ponyta.Confide;
import Moves.Ponyta.Flamethrower;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Rapidash extends Ponyta {
    public Rapidash(String name, int level) {
        super(name, level);
        setStats(65, 100, 70, 80, 80, 105);
        setType(Type.FIRE);
        addMove(new Flamethrower());
    }
}
