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

    public Book addBooks(Book book, int bookCount, String reasonOfParish, int librarianId, String comment)  {

        Book savedBook = bookRepository.save(book);
        addBookToOtherTables(savedBook, bookCount, reasonOfParish, librarianId, comment);

        return book;
    }

    public List<Book> addBooksFromFile(MultipartFile file, int librarianId, String reasonOfParish,
                                                            String comment, String fileFormat)  {
        List<Book> books;
        ParserFactory parserFactory = new ParserFactory();
        try {
            FileParser parser = parserFactory.createParser(Book.class, file, fileFormat);
            books = parser.read(Book.class, file);
            log.info(books);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IOException");
        }

        List<Book> savedBooks =  bookRepository.saveAll(books);

        for (Book book : savedBooks) {
            addBookToOtherTables(book, savedBooks.size(), reasonOfParish, librarianId, comment);
        }
        return bookRepository.findAll();
    }

    public void addBookToOtherTables(Book book, int bookCount, String reasonOfParish, int librarianId, String comment){

        Librarian librarian = repository.findById(librarianId).orElseThrow(LibrarianNotFound::new);
        LibrarianDepartment librarianDepartment = librarianDepartmentRepository.findByLibrarian(librarian);
        LibraryDepartment libraryDepartment = librarianDepartment.getLibraryDepartment();

        BookStorage bookStorage = new BookStorage();
        bookStorage.setBook(book);
        bookStorage.setLibraryDepartment(libraryDepartment);
        bookStorage.setTotalCount(bookCount);
        bookStorage.setAvailableCount(bookCount);
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
            try {
                throw new RuntimeException("No such reason of parish");
            } catch (RuntimeException e) {
                log.info(e.getMessage());
            }

        bookTransferRepository.save(bookTransfer);
    }

}
