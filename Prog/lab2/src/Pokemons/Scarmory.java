package Pokemons;

import Moves.Scarmory.DoubleTeam;
import Moves.Scarmory.MetalClaw;
import Moves.Scarmory.Rest;
import Moves.Scarmory.SandAttack;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Scarmory extends Pokemon {
    public Scarmory(String name, int level) {
        super(name, level);
        setStats(65, 80, 140, 40, 70, 70);
        setType(Type.STEEL);
        setMove(new DoubleTeam(), new MetalClaw(), new Rest(), new SandAttack());
    }
}
