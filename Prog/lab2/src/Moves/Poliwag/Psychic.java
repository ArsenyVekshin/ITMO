package Moves.Poliwag;

import ru.ifmo.se.pokemon.*;

public class Psychic extends SpecialMove{
    public Psychic() {
        super(Type.PSYCHIC, 90, 1);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect effect = new Effect().turns(-1).chance(0.1).stat(Stat.SPECIAL_DEFENSE, -1);
        pokemon.addEffect(effect);
    }

    @Override
    protected String describe(){
        return "использует Psychic (-1 спец защиты противника)";
    }
}
