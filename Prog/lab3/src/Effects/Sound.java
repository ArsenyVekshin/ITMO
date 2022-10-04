package Effects;

import People.Human;

public class Sound {
    private String name = "default";
    Sound(String name){
        this.name=name;
    }

    public void effectOn(Human... arg) {
        if (arg == null) return;
        for (Human human : arg) {
            human.setStatus(name);
            System.out.print(human.getName() + " ");
        }
        System.out.println("- " + name);
    }
}
