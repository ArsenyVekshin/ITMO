package com.ArsenyVekshin.task3;

import com.ArsenyVekshin.task3.model.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Subject trash = new Subject("Мусор", true);
        Subject lighter = new Subject("Фонарь", false);

        // Создание локаций
        Location surface = new Location("Поверхность");
        Location underground = new Location("Подземные галереи", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), SubjStatus.LOCKED);
        Location enter1 = new Location("Проход 1", new ArrayList<>(List.of(trash.clone())), new ArrayList<>(), new ArrayList<>(), SubjStatus.LOCKED);
        Location enter2 = new Location("Проход 2", new ArrayList<>(List.of(trash.clone(), trash.clone())), new ArrayList<>(), new ArrayList<>(), SubjStatus.LOCKED);

        // Связь между локациями
        enter1.getConnectedLocations().addAll(List.of(surface, underground));
        enter2.getConnectedLocations().addAll(List.of(surface, underground));

        surface.getConnectedLocations().addAll(List.of(underground, enter1, enter2));
        surface.getNestedLocations().add(underground);
        underground.getConnectedLocations().addAll(List.of(surface, enter1, enter2));


        // Создание персонажей
        Alive whale = new Alive("кит", true, null);
        Human zaphod = new Human("Зафод", true, surface, new ArrayList<>(List.of(lighter)));
        Human marvin = new Human("Марвин", true, surface, new ArrayList<>());

        // Падение кита и разрушение земли
        System.out.println(whale.getName() + " падает на " + surface.getName());
        Subject whaleRemains = whale.dead();
        surface.feelByObjects(whaleRemains);
        surface.destroy();
        underground.setSubjStatus(SubjStatus.SMOKE);


        // Расчистка завалов
        while (enter1.isLocked() && enter2.isLocked()) {
            enter1.clearLocationFromTrash(zaphod);
            enter2.clearLocationFromTrash(marvin);
        }
        System.out.println(enter1.getName() + " : " + enter1.getSubjStatus());
        System.out.println(enter2.getName() + " : " + enter2.getSubjStatus());

        // Освещение прохода
        try {
            zaphod.useTool(lighter);
            underground.observe(zaphod, lighter);
        } catch (Exception e) {
            System.out.println("Исследование не удалось: " + e.getMessage());
        }
    }
}