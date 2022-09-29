package Pokemons;

import Moves.Poliwag.*;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Poliwag extends Pokemon {
    public Poliwag(String name, int level) {
        super(name, level);
        setStats(40, 50, 40, 40, 40, 90);
        setType(Type.WATER);
        setMove(new Psychic(), new Confide());
    }
}
