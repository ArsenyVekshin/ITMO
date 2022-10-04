package Moves.Scarmory;

import ru.ifmo.se.pokemon.*;

public class SandAttack extends StatusMove{
    public SandAttack() {
        super(Type.GROUND, 0, 1);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect effect = new Effect().turns(1).stat(Stat.ACCURACY, -15);
        pokemon.addEffect(effect);
    }

    @Override
    protected String describe(){
        return "использует Sand Attack (точность противника -15)";
    }
}
