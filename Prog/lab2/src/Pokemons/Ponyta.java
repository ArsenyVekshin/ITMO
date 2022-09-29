package Pokemons;

import Moves.Ponyta.Agility;
import Moves.Ponyta.Confide;
import Moves.Ponyta.Flamethrower;
import Moves.Scarmory.DoubleTeam;
import Moves.Scarmory.MetalClaw;
import Moves.Scarmory.Rest;
import Moves.Scarmory.SandAttack;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Ponyta extends Pokemon {
    public Ponyta(String name, int level) {
        super(name, level);
        setStats(50, 85, 55, 65, 65, 90);
        setType(Type.FIRE);
        setMove(new Agility(), new Confide(), new Flamethrower());
    }
}
