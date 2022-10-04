package Moves.Scarmory;

import ru.ifmo.se.pokemon.*;

public class MetalClaw extends PhysicalMove{
    public MetalClaw() {
        super(Type.STEEL, 50, 0.95);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect effect = new Effect().turns(1).chance(0.95).stat(Stat.ACCURACY, -15);
        pokemon.addEffect(effect);
        //pokemon.setMod(Stat.ACCURACY, (int)pokemon.getStat(Stat.ACCURACY) - 15);
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        Effect effect = new Effect().turns(1).chance(0.1).stat(Stat.ATTACK, 1);
        pokemon.addEffect(effect);
        //pokemon.setMod(Stat.ACCURACY, (int)pokemon.getStat(Stat.ACCURACY) - 15);
    }

    @Override
    protected String describe(){
        return "использует Metal Claw (точность противника -15)";
    }
}
