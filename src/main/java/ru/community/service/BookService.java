package ru.community.service;


import com.poiji.bind.Poiji;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.community.entity.*;
import ru.community.exception.LibrarianNotFound;
import ru.community.parser.MultipartToFile;
import ru.community.repo.LibraryRepository;
import ru.community.repository.*;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    private final LibrarianDepartmentRepository librarianDepartmentRepository;
    private final BookStorageRepository bookStorageRepository;
    private final BookTransferRepository bookTransferRepository;
    private final LibraryRepository libraryRepository;


    public Book addBook(Book book){
       return repository.save(book);
    }

    public Book getBook(String author, String title){
        return repository.findByAuthorAndTitle(author, title);
    }

    public List<Book> getAllBooks(){
        return repository.findAll();
    }


    public Book addBooks(Book book, Map<String, String> param, int librarianId){
        Librarian librarian = libraryRepository.findById(librarianId).orElseThrow(LibrarianNotFound::new);
        LibrarianDepartment librarianDepartment = librarianDepartmentRepository.findByLibrarian(librarian);
        LibraryDepartment libraryDepartment = librarianDepartment.getLibraryDepartment();

        repository.save(book);

        BookStorage bookStorage = new BookStorage();
        bookStorage.setBook(book);
        bookStorage.setDepartmentId(libraryDepartment.getId());
        bookStorage.setTotalCount(Integer.parseInt(param.get("bookCount")));
        bookStorage.setAvailableCount(Integer.parseInt(param.get("bookCount")));
        bookStorageRepository.save(bookStorage);

        BookTransfer bookTransfer = new BookTransfer();
        bookTransfer.setBookStorage(bookStorage);
        bookTransfer.setRegisterOfParish(param.get("registerOfParish"));
        bookTransfer.setRegisterDate(LocalDate.now());
        bookTransferRepository.save(bookTransfer);

        return book;
    }

    public List<Book> addBooksFromExcel(MultipartFile file, int librarianId, String registerOfParish){
        File file1 = MultipartToFile.uploadToLocal(file);
        List<Book> books = repository.saveAll(Poiji.fromExcel(file1, Book.class));

        for (Book book : books) {
            Librarian librarian = libraryRepository.findById(librarianId).orElseThrow(LibrarianNotFound::new);
            LibrarianDepartment librarianDepartment = librarianDepartmentRepository.findByLibrarian(librarian);
            LibraryDepartment libraryDepartment = librarianDepartment.getLibraryDepartment();

            BookStorage bookStorage = new BookStorage();
            bookStorage.setBook(book);
            bookStorage.setDepartmentId(libraryDepartment.getId());
            bookStorage.setTotalCount(books.size());
            bookStorage.setAvailableCount(books.size());
            bookStorageRepository.save(bookStorage);

            BookTransfer bookTransfer = new BookTransfer();
            bookTransfer.setBookStorage(bookStorage);
            bookTransfer.setRegisterOfParish(registerOfParish);
            bookTransfer.setRegisterDate(LocalDate.now());
            bookTransferRepository.save(bookTransfer);
        }
        return books;
    }

}
