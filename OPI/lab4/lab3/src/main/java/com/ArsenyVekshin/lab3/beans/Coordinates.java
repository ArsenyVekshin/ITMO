package com.ArsenyVekshin.lab3.beans;

import lombok.*;

import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Named("coordinates")
@RequestScoped
public class Coordinates implements Serializable {
    private double x;
    private double y;
    private double r;
}
