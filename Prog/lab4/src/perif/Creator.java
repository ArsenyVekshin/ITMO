package perif;

import exception.WrongCreatorException;
import obj.Alive.Human;
import obj.Entity;

public interface Creator {
    public void createSmth(Human human, Entity obj) throws WrongCreatorException;
}
