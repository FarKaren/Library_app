package ru.community.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.community.dto.ReaderEditDto;
import ru.community.entity.Reader;
import ru.community.exception.ReaderNotFound;
import ru.community.repository.ReaderRepository;

import javax.validation.Valid;
import java.util.List;

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

    @Valid
    public Reader editReader(int id, ReaderEditDto readerEditDto) {
        var reader = repository.findById(id).orElseThrow(ReaderNotFound::new);
        if (readerEditDto.getPhoneNumber() != null) {
            reader.setPhoneNumber(readerEditDto.getPhoneNumber());
        }
        if (readerEditDto.getEmail() != null) {
            reader.setEmail(readerEditDto.getEmail());
        }
        if (readerEditDto.getDateOfBirth() != null) {
            reader.setDateOfBirth(readerEditDto.getDateOfBirth());
        }
        repository.save(reader);
        return reader;
    }
}
