package ru.community.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.community.entity.Reader;
import ru.community.exception.Message;
import ru.community.exception.SmartToolsException;
import ru.community.repository.ReaderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository repository;

    public void addReader(Reader reader) {
        if (repository.findByNameAndSurnameAndDateOfBirth(reader.getName(), reader.getSurname(), reader.getDateOfBirth()).isPresent()) {
            throw new SmartToolsException(Message.READER_ALREADY_EXISTS);
        } else {
            repository.save(reader);
        }
    }

    public Reader getReader(int id) {
        return repository.findById(id).orElseThrow(() -> new SmartToolsException(Message.READER_NOT_FOUND));
    }

    public List<Reader> getAllReaders() {
        return repository.findAll();
    }

    public void deleteReader(int id) {
        Reader reader = repository.findById(id).orElseThrow(() -> new SmartToolsException(Message.READER_NOT_FOUND));
        repository.delete(reader);
    }

    public Reader editReader(int id, Reader editReader) {
        var reader = repository.findById(id).orElseThrow(() -> new SmartToolsException(Message.READER_NOT_FOUND));
        reader.setPhoneNumber(editReader.getPhoneNumber());
        reader.setEmail(editReader.getEmail());
        reader.setDateOfBirth(editReader.getDateOfBirth());
        repository.save(reader);
        return reader;
    }
}
