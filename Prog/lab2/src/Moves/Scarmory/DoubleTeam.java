package Moves.Scarmory;

import ru.ifmo.se.pokemon.*;
import ru.ifmo.se.pokemon.Type;

public class DoubleTeam extends StatusMove{
    public DoubleTeam() {
        super(Type.NORMAL, 0, -1);
    }

    @Override
    protected boolean checkAccuracy(Pokemon att, Pokemon def){
        return true;
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        Effect effect = new Effect().turns(-1).stat(Stat.EVASION, 1);
        pokemon.addEffect(effect);
    }

    @Override
    protected String describe(){
        return "использует Double Team (уклонение *2)";
    }
}
