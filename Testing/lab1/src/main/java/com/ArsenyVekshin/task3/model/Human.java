package com.ArsenyVekshin.task3.model;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Human extends Alive{

    private List<Subject> inventory = new ArrayList<>();

    public void said(String mes){
        if(!mayBeActor()) throw new IllegalArgumentException(getName() + " не может совершать действия");
        System.out.println(getName() + " говорит: \"" + mes + "\"");
    }

    public void useTool(Subject tool){
        if(!mayBeActor()) throw new IllegalArgumentException(getName() + " не может совершать действия");
        if(inventory.contains(tool)) throw new IllegalArgumentException("Невозможно использовать инструмент");
        System.out.println(getName() + " использует " + tool.getName());
    }

}
