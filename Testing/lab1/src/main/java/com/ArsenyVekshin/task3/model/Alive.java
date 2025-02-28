package com.ArsenyVekshin.task3.model;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Alive implements Named {

    private String name;
    private boolean isAlive = true;

    private Location location;

    public Subject dead() {
        this.isAlive = false;
        return new Subject("Останки " + name, true);
    }

    public boolean mayBeActor() {
        return isAlive;
    }

}
