package com.ArsenyVekshin.task3.model;

import lombok.*;


import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private String name;

    private List<Named> nestedObj = new ArrayList<>();

    private List<Location> nestedLocations = new ArrayList<>();
    private List<Location> connectedLocations = new ArrayList<>();

    private SubjStatus subjStatus;

    public void moveHuman(Human actor) {
        checkIsInside(actor);
        System.out.println(actor.getName() + " ходит по " + this.getName());
    }

    public void moveHuman(Human actor, Location dest) {
        checkIsInside(actor);
        checkLocationConnection(dest);

        nestedObj.remove(actor);
        addNested(actor);
    }

    public void moveSubj(Subject subj, Human actor, Location dest) {
        checkIsInside(subj);
        checkIsInside(actor);
        checkLocationConnection(dest);

        nestedObj.remove(actor);
        addNested(actor);
    }

    public void addNested(Named obj) {
        System.out.println(obj.getName() + " находится в " + this.name);
        nestedObj.add(obj);
    }

    public boolean isLocked() {
        for(Named obj : this.nestedObj) {
            if(obj instanceof Subject && ((Subject) obj).isTrash()) {
                return true;
            }
        }
        return false;
    }

    public void clearLocationFromTrash(Human actor) {
        checkActorStatus(actor);
        checkLocationConnection(actor.getLocation());
        if(this.subjStatus != SubjStatus.LOCKED)
            throw new IllegalArgumentException(this.name + " не требует отчистки");
        checkIsInside(actor);
        for(Named obj : this.nestedObj) {
            if(obj instanceof Subject && ((Subject) obj).isTrash()) {
                nestedObj.remove(obj);
                break;
            }
        }
    }

    private void checkActorStatus(Alive actor) {
        if(!actor.mayBeActor())
            throw new IllegalArgumentException(actor.getName() + " не может совершать действия");
    }

    private void checkIsInside(Named obj) {
        if (!nestedObj.contains(obj))
            throw new IllegalArgumentException("Объект не находится внутри");
    }

    private void checkLocationConnection(Location dest) {
        if(!connectedLocations.contains(dest) || !nestedLocations.contains(dest))
            throw new IllegalArgumentException("Целевая локация не связана с текущей");
    }
}
