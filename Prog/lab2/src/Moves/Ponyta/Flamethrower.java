package Moves.Ponyta;

import ru.ifmo.se.pokemon.*;

public class Flamethrower extends SpecialMove{
    public Flamethrower() {
        super(Type.FIRE, 90, 1);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect effect = new Effect().turns(-1).chance(0.1).condition(Status.BURN);
        pokemon.addEffect(effect);
    }

    @Override
    protected String describe(){
        return "использует Flamethrower";
    }
}
