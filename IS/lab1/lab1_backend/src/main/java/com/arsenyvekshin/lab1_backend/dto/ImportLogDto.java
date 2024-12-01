package com.arsenyvekshin.lab1_backend.dto;

import com.arsenyvekshin.lab1_backend.entity.ImportLogNote;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запись лога о импорте из файла")
public class ImportLogDto {
    @Schema(description = "id", example = "1")
    private Long id;

    @Column(name = "creationDate")
    private java.time.LocalDate creationDate;

    @Column(name = "owner")
    private String owner;

    @Column(name = "number")
    private Long number = 0L;

    @Column(name = "successful")
    private boolean successful = false;

    public ImportLogDto(ImportLogNote note) {
        this.id = note.getId();
        this.creationDate = note.getCreationDate();
        this.owner = note.getOwner().getUsername();
        this.number = note.getNumber();
        this.successful = note.isSuccessful();
    }

}

