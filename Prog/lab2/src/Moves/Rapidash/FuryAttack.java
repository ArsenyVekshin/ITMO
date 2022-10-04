package Moves.Rapidash;

import ru.ifmo.se.pokemon.*;

public class FuryAttack extends PhysicalMove{
    public FuryAttack() {
        super(Type.NORMAL, 15, 0.85, 1, 3);
    }

    @Override
    protected String describe(){
        return "использует Furry Attack";
    }
}
