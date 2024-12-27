package com.arsenyvekshin.lab1_backend.dto;

import com.arsenyvekshin.lab1_backend.entity.ImportLogNote;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("id")
    private Long id;

    @JsonProperty("creationDate")
    private java.time.LocalDate creationDate;

    @JsonProperty("owner")
    private String owner;

    @JsonProperty("number")
    private Long number = 0L;

    @JsonProperty("successful")
    private boolean successful = false;

    @JsonProperty("key")
    private String key;

    @JsonProperty("error")
    private String error;

    public ImportLogDto(ImportLogNote note) {
        this.id = note.getId();
        this.creationDate = note.getCreationDate();
        this.owner = note.getOwner().getUsername();
        this.number = note.getNumber();
        this.successful = note.isSuccessful();
        this.key = note.getKey();
        this.error = note.getError();
    }

}

