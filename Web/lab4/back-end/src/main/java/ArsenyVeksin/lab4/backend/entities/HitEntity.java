package ArsenyVeksin.lab4.backend.entities;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hitsResults")
public class HitEntity {
    @Id
    @ToString.Exclude
    private long id;

    private long ownerId;
    private boolean removed;

    private double x;
    private double y;
    private double r;
    private String creationTime;
    private boolean result;

}
