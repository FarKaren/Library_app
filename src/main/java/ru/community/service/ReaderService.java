package ru.community.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.community.entity.Reader;
import ru.community.exception.ReaderNotFound;
import ru.community.repo.ReaderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository repository;

    public void addReader(Reader reader){
         repository.save(reader);
    }

    public Reader getReader(int id){
        return repository.findById(id).orElseThrow(ReaderNotFound::new);
    }

    public List<Reader> getAllReaders(){
        return repository.findAll();
    }

    public void deleteReader(int id){
        Reader reader = repository.findById(id).orElseThrow(ReaderNotFound::new);
        repository.delete(reader);
    }
}
