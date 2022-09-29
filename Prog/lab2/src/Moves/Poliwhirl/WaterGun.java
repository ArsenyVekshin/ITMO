package Moves.Poliwhirl;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class WaterGun extends SpecialMove {
    public WaterGun() {
        super(Type.WATER, 40, 100);
    }

    @Override
    protected String describe(){
        return "использует Water Gun";
    }
}
