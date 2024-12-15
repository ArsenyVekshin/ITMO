package com.arsenyvekshin.lab1_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ImportLog")
public class ImportLogNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creationDate", nullable = false, updatable = false)
    private java.time.LocalDate creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    private User owner;

    @Column(name = "number")
    private Long number = 0L;

    @Column(name = "successful")
    private boolean successful = false;

    @Column(name = "key")
    private String key;

    public ImportLogNote(User user, Long number) {
        this.owner = user;
        this.number = number;
    }

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDate.now(); // Set the current date
    }
}
