package ArsenyVekshin.lab6.general.collectionElems.data;

import java.util.HashMap;
import java.util.function.Supplier;

public abstract class Entity {
    public abstract void init(HashMap<String, Object> values);
    public abstract HashMap<String, Object> getValues();
    public abstract Supplier<Entity> getConstructorReference();
}
