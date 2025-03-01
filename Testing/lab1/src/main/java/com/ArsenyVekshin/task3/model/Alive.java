package com.ArsenyVekshin.task3.model;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Alive implements Named {

    protected String name;
    protected boolean isAlive = true;

    protected Location location;

    public Subject dead() {
        System.out.println(this.name + " умирает");
        this.isAlive = false;
        return new Subject("Останки " + name, true);
    }

    public boolean mayBeActor() {
        return isAlive;
    }

}
