package Moves.Poliwrath;

import ru.ifmo.se.pokemon.*;

public class DynamicPunch extends PhysicalMove{
    public DynamicPunch() {
        super(Type.FIGHTING, 100, 50);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect effect = new Effect().turns(-1).condition(Status.PARALYZE);
        pokemon.addEffect(effect);
    }

    @Override
    protected String describe(){
        return "использует Dynamic Punch (паралич противника)";
    }
}
