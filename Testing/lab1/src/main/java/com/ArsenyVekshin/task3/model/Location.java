package com.ArsenyVekshin.task3.model;

import lombok.*;


import java.util.ArrayList;
import java.util.Iterator;
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

    public Location(String name) {
        this.name = name;
    }

    public void moveHuman(Human actor) {
        checkIsInside(actor);
        System.out.println(actor.getName() + " ходит по " + this.getName());
    }

    public void moveHuman(Human actor, Location dest) {
        checkActorStatus(actor);
        //checkIsInside(actor);
        checkLocationConnection(dest);

        nestedObj.remove(actor);
        //addNested(actor);
        actor.setLocation(dest);
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

        if (this.subjStatus != SubjStatus.LOCKED)
            throw new IllegalArgumentException(this.name + " не требует очистки");

        Iterator<Named> iterator = nestedObj.iterator();
        while (iterator.hasNext()) {
            Named obj = iterator.next();
            if (obj instanceof Subject && ((Subject) obj).isTrash()) {
                iterator.remove();
                System.out.println(actor.getName() + " убирает " + obj.getName() + " из " + this.getName());
                break;
            }
        }

        if (isLocked()) {
            this.subjStatus = SubjStatus.AVAILABLE;
        }
    }

    public void destroy() {
        System.out.println(this.name + " разрушается");
        this.subjStatus = SubjStatus.LOCKED;
        Subject wreckage = new Subject("Обломки", true);
        feelByObjects(wreckage);
    }

    public void feelByObjects(Subject subj) {
        addNested(subj.clone());
        for(Location loc : nestedLocations){
            loc.feelByObjects(subj);
        }
    }


    public void observe(Alive actor, Subject tool) {
        checkActorStatus(actor);
        if (subjStatus == SubjStatus.HIDDEN || subjStatus == SubjStatus.SMOKE)
            throw new IllegalArgumentException(name + " не может быть осмотрена");
        System.out.print(name + " осмотрена " + actor);
        if(tool != null) System.out.print(" при помощи " + tool.getName());
        System.out.println();

        this.subjStatus = SubjStatus.OBSERVED;
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
        if(!connectedLocations.contains(dest) && !nestedLocations.contains(dest) && !dest.getNestedLocations().contains(this))
            throw new IllegalArgumentException("Целевая локация не связана с текущей");
    }
}
