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
}
