package task3;

import com.ArsenyVekshin.task3.model.Human;
import com.ArsenyVekshin.task3.model.Location;
import com.ArsenyVekshin.task3.model.Subject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task3Tests {

    @Test
    @DisplayName("Использования инструментов")
    public void toolUsageTest() {
        Subject a = new Subject("a", false);
        Subject b = new Subject("b", false);
        Subject c = new Subject("a", true);

        Human human = new Human("aa", true, null, new ArrayList<>(List.of(a, c)));
        Human human2 = new Human("bb", false, null, new ArrayList<>(List.of(a, c)));

        human.useTool(a);
        assertThrows(IllegalArgumentException.class, () -> human.useTool(b));
        assertThrows(IllegalArgumentException.class, () -> human.useTool(c));
        assertThrows(IllegalArgumentException.class, () -> human2.useTool(a));
    }

    @Test
    @DisplayName("Перемещение между локациями")
    public void moveTest(){
        Location loc1 = new Location("loc1");
        Location loc2 = new Location("loc2");
        Location loc3 = new Location("loc3");
        Location loc4 = new Location("loc4");

        loc1.getConnectedLocations().add(loc2);
        loc2.getConnectedLocations().add(loc3);

        loc2.getNestedLocations().add(loc3);



        Human human = new Human("aa", true, loc1, new ArrayList<>());
        Human human2 = new Human("bb", false, loc2, new ArrayList<>());


        assertThrows(IllegalArgumentException.class, () -> loc1.moveHuman(human, loc4));
        assertThrows(IllegalArgumentException.class, () -> loc4.moveHuman(human, loc1));

        assertThrows(IllegalArgumentException.class, () -> loc1.moveHuman(human2, loc2));


        // бегаем по локациям
        loc1.moveHuman(human, loc2);
        assertEquals(loc2, human.getLocation());
        loc2.moveHuman(human, loc3);
        assertEquals(loc3, human.getLocation());
        loc3.moveHuman(human, loc2);
        assertEquals(loc2, human.getLocation());

    }


}
