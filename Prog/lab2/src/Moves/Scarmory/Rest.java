package Moves.Scarmory;

import ru.ifmo.se.pokemon.*;

public class Rest extends StatusMove{
    public Rest() {
        super(Type.PSYCHIC, 0, -1);
    }

    @Override
    protected boolean checkAccuracy(Pokemon att, Pokemon def){
        return true;
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect effect = new Effect().turns(2).condition(Status.SLEEP);
        pokemon.addEffect(effect);
        pokemon.setMod(Stat.HP, (int)Stat.HP.ordinal());
    }

    @Override
    protected String describe(){
        return "использует Rest (полный хил)";
    }
}
