package Moves.Ponyta;

import ru.ifmo.se.pokemon.*;

public class Agility extends StatusMove{
    public Agility() {
        super(Type.PSYCHIC, 0, -1);
    }

    @Override
    protected boolean checkAccuracy(Pokemon att, Pokemon def){
        return true;
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        Effect effect = new Effect().turns(2).stat(Stat.SPEED, 2);
        pokemon.addEffect(effect);
    }

    @Override
    protected String describe(){
        return "использует Agility (скорость +2)";
    }
}
