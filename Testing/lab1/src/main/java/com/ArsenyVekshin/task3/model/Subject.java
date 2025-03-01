package com.ArsenyVekshin.task3.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subject implements Named, Cloneable{
    private String name;
    private boolean isTrash = false;

    @Override
    public Subject clone() {
        try {
            Subject clone = (Subject) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
