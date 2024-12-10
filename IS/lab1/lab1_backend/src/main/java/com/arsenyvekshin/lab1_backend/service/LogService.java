package com.arsenyvekshin.lab1_backend.service;

import com.arsenyvekshin.lab1_backend.dto.ImportLogDto;
import com.arsenyvekshin.lab1_backend.entity.ImportLogNote;
import com.arsenyvekshin.lab1_backend.entity.Role;
import com.arsenyvekshin.lab1_backend.entity.User;
import com.arsenyvekshin.lab1_backend.repository.ImportLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class LogService {
    private final UserService userService;
    private final ImportLogRepository importLogRepository;

    @Transactional(readOnly = true)
    public List<ImportLogDto> getImportLog() {
        User currentUser = userService.getCurrentUser();
        return importLogRepository.findAll().stream()
                .filter(elem -> (elem.getOwner().getRole() == Role.ADMIN || elem.getOwner() == currentUser))
                .map(ImportLogDto::new)
                .toList();
    }

    public Long addImportLog(Long number) {
        ImportLogNote note = new ImportLogNote(userService.getCurrentUser(), number);
        importLogRepository.save(note);
        return note.getId();
    }


    public void markImportLogSuccess(Long operationId, Long number) {
        ImportLogNote note = importLogRepository.getById(operationId);
        note.setSuccessful(true);
        note.setNumber(number);
        importLogRepository.save(note);
    }

}
