package ru.community.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.community.entity.*;
import ru.community.exception.LibrarianNotFound;
import ru.community.parser.FileParser;
import ru.community.parser.ParserFactory;
import ru.community.repository.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class LibraryService {

    private final LibraryRepository repository;
    private final LibrarianDepartmentRepository librarianDepartmentRepository;
    private final BookRepository bookRepository;
    private final BookStorageRepository bookStorageRepository;
    private final BookTransferRepository bookTransferRepository;



    public void addLibrarian(Librarian librarian) {
        repository.save(librarian);
    }

    public Librarian getLibrarian(int librarianId) {
        return repository.findById(librarianId).orElseThrow(LibrarianNotFound::new);
    }

    public List<Librarian> getAllLibrarian() {
        return repository.findAll();
    }

    public void deleteLibrarian(int id) {
        Librarian librarian = repository.findById(id).orElseThrow(LibrarianNotFound::new);
        repository.delete(librarian);
    }

    public Book addBooks(Book book, int bookCount, String reasonOfParish, int librarianId, String comment) throws Exception {
        Librarian librarian = repository.findById(librarianId).orElseThrow(LibrarianNotFound::new);
        LibrarianDepartment librarianDepartment = librarianDepartmentRepository.findByLibrarian(librarian);
        LibraryDepartment libraryDepartment = librarianDepartment.getLibraryDepartment();

        Book savedBook = bookRepository.save(book);

        BookStorage bookStorage = new BookStorage();
        bookStorage.setBook(savedBook);
        bookStorage.setDepartmentId(libraryDepartment.getId());
        bookStorage.setTotalCount(bookCount);
        bookStorage.setAvailableCount(bookCount);
        bookStorageRepository.save(bookStorage);

        BookTransfer bookTransfer = new BookTransfer();
        bookTransfer.setBookStorage(bookStorage);
        bookTransfer.setLibrarian(librarian);
        bookTransfer.setComment(comment);
        bookTransfer.setRegisterDate(LocalDate.now());

        for (ReasonOfParish c : ReasonOfParish.values())
            if (c.getDescription().equals(reasonOfParish))
                bookTransfer.setReasonOfParish(c);

        if (bookTransfer.getReasonOfParish() == null)
            throw new Exception("causeOfParish is NULL");

        bookTransferRepository.save(bookTransfer);
        log.info("We are here!!!!!!!!!!!!!");

        return book;
    }

    public List<Book> addBooksFromFile(MultipartFile file, int librarianId, String reasonOfParish,
                                                            String comment, String fileFormat) throws Exception {
        List<Book> books;
        try {
            FileParser parser = ParserFactory.createParser(Book.class, file, fileFormat);
            books = parser.read(Book.class, file);
            log.info(books);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("oops");
        }

        List<Book> savedBooks =  bookRepository.saveAll(books);
        Librarian librarian = repository.findById(librarianId).orElseThrow(LibrarianNotFound::new);
        LibrarianDepartment librarianDepartment = librarianDepartmentRepository.findByLibrarian(librarian);
        LibraryDepartment libraryDepartment = librarianDepartment.getLibraryDepartment();

        for (Book book : savedBooks) {

            BookStorage bookStorage = new BookStorage();
            bookStorage.setBook(book);
            bookStorage.setDepartmentId(libraryDepartment.getId());
            bookStorage.setTotalCount(books.size());
            bookStorage.setAvailableCount(books.size());
            bookStorageRepository.save(bookStorage);

            BookTransfer bookTransfer = new BookTransfer();
            bookTransfer.setBookStorage(bookStorage);
            bookTransfer.setLibrarian(librarian);
            bookTransfer.setComment(comment);
            bookTransfer.setRegisterDate(LocalDate.now());

            for (ReasonOfParish c : ReasonOfParish.values())
                if(c.getDescription().equals(reasonOfParish))
                    bookTransfer.setReasonOfParish(c);
            if(bookTransfer.getReasonOfParish() == null)
                throw new Exception("No such reason of parish");

            bookTransferRepository.save(bookTransfer);
        }
        return bookRepository.findAll();
    }
}
