package ru.community.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.community.entity.Reader;
import ru.community.exception.ReaderNotFound;
import ru.community.repository.ReaderRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository repository;

    public void addReader(Reader reader) {
        repository.save(reader);
    }

    public Reader getReader(int id) {
        return repository.findById(id).orElseThrow(ReaderNotFound::new);
    }

    public List<Reader> getAllReaders() {
        return repository.findAll();
    }

    public void deleteReader(int id) {
        Reader reader = repository.findById(id).orElseThrow(ReaderNotFound::new);
        repository.delete(reader);
    }

    public Reader editReader(int id, Map<String, String> allParams) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if (allParams.containsKey("phone_number")) {
            repository.findById(id).orElseThrow(ReaderNotFound::new).setPhoneNumber(allParams.get("phone_number"));
        }
        if (allParams.containsKey("email")) {
            repository.findById(id).orElseThrow(ReaderNotFound::new).setEmail(allParams.get("email"));
        }
        if (allParams.containsKey("date_of_birth")) {
            repository.findById(id).orElseThrow(ReaderNotFound::new).setDateOfBirth(LocalDate.parse(allParams.get("date_of_birth"), formatter));
        }
        repository.save(repository.findById(id).orElseThrow(ReaderNotFound::new));
        return repository.findById(id).orElseThrow(ReaderNotFound::new);
    }
}
