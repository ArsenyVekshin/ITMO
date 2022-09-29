package Moves.Ponyta;

import ru.ifmo.se.pokemon.*;

public class Confide extends StatusMove{
    public Confide() {
        super(Type.NORMAL, 0, -1);
    }

    @Override
    protected boolean checkAccuracy(Pokemon att, Pokemon def){
        return true;
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect effect = new Effect().turns(-1).stat(Stat.SPECIAL_ATTACK, -1);
        pokemon.addEffect(effect);
    }

    @Override
    protected String describe(){
        return "использует Confide";
    }
}
