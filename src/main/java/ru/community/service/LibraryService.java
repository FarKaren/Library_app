package ru.community.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.community.entity.Book;
import ru.community.entity.BookBinding;
import ru.community.entity.BookStorage;
import ru.community.entity.BookTransfer;
import ru.community.entity.Librarian;
import ru.community.entity.LibrarianDepartment;
import ru.community.entity.LibraryDepartment;
import ru.community.entity.Reader;
import ru.community.entity.Status;
import ru.community.entity.Reason;
import ru.community.exception.LibraryException;
import ru.community.exception.Message;
import ru.community.parser.FileReader;
import ru.community.parser.ParserFactory;
import ru.community.repository.BookBindingRepository;
import ru.community.repository.BookRepository;
import ru.community.repository.BookStorageRepository;
import ru.community.repository.BookTransferRepository;
import ru.community.repository.LibrarianDepartmentRepository;
import ru.community.repository.LibrarianRepository;
import ru.community.repository.LibraryDepartmentRepository;
import ru.community.repository.ReaderRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class LibraryService {

    private final LibrarianRepository librarianRepository;
    private final LibrarianDepartmentRepository librarianDepartmentRepository;
    private final BookRepository bookRepository;
    private final BookStorageRepository bookStorageRepository;
    private final BookTransferRepository bookTransferRepository;
    private final ParserFactory parserFactory;
    private final ReaderRepository readerRepository;
    private final LibraryDepartmentRepository libraryDepartmentRepository;
    private final BookBindingRepository bookBindingRepository;

    public void addLibrarian(Librarian librarian) {
        if (librarianRepository.findByNameAndSurnameAndDateOfBirth(librarian.getName(), librarian.getSurname(), librarian.getDateOfBirth()).isPresent()) {
            throw new LibraryException(Message.LIBRARIAN_ALREADY_EXISTS);
        } else {
            librarianRepository.save(librarian);
        }
    }

    public Librarian getLibrarian(int librarianId) {
        return librarianRepository.findById(librarianId)
                .orElseThrow(() -> new LibraryException(Message.LIBRARIAN_NOT_FOUND));
    }

    public List<Librarian> getAllLibrarian() {
        return librarianRepository.findAll();
    }

    public void deleteLibrarian(int id) {
        Librarian librarian = librarianRepository.findById(id)
                .orElseThrow(() -> new LibraryException(Message.LIBRARIAN_NOT_FOUND));
        librarianRepository.delete(librarian);
    }

    public Librarian editLibrarian(int id, Librarian librarian){
        Librarian lib = librarianRepository.findById(id)
                .orElseThrow(() -> new LibraryException(Message.LIBRARIAN_NOT_FOUND));
         lib.setPhoneNumber(librarian.getPhoneNumber());
         lib.setDateOfBirth(librarian.getDateOfBirth());
        return librarianRepository.save(lib);
    }

    public List<BookBinding> addBookAndReaderToBindingBooks(int readerId, int departmentId, List<Integer> booksId) {
        List<BookBinding> bookBindings = new ArrayList<>();

        Reader reader = readerRepository.findById(readerId)
                .orElseThrow(() -> new LibraryException(Message.READER_NOT_FOUND));

        LibraryDepartment libraryDepartment = libraryDepartmentRepository.findById(departmentId)
                .orElseThrow(() -> new LibraryException(Message.DEPARTMENT_NOT_FOUND));

        for (Integer id : booksId) {
            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new LibraryException(Message.BOOK_NOT_FOUND))
                    ;
            BookBinding bookBinding = bookBindingRepository.save(new BookBinding(book, reader, LocalDate.now()
                    , LocalDate.now().plusDays(30), Status.ACTUAL, libraryDepartment));

            bookBindings.add(bookBinding);
        }

        return bookBindings;
    }

    public Book addBooks(Book book, int bookCount, Reason reason, int librarianId, String comment) {
        Book savedBook = bookRepository.save(book);
        addBookToOtherTables(savedBook, bookCount, reason, librarianId, comment);
        return book;
    }


    public List<Book> addBooksFromFile(MultipartFile file, int librarianId, Reason reason, String comment) {
        try {
            FileReader parser = parserFactory.createParser(file);
            List<Book> books = parser.read(Book.class, file);
            log.info("Data from file {}", books);

            List<Book> savedBooks = bookRepository.saveAll(books);

            for (Book book : savedBooks) {
                addBookToOtherTables(book, savedBooks.size(), reason, librarianId, comment);
            }
            return bookRepository.findAll();

        } catch (LibraryException e) {
            throw e;
        } catch (Exception e) {
            throw new LibraryException(Message.TECHNICAL_ERROR);
        }
    }

    @Transactional
    public void addBookToOtherTables(Book book, int bookCount, Reason reason, int librarianId, String comment) {
        if (!Reason.isReasonPresent(reason)) throw new LibraryException(Message.REASON_NOT_FOUND);

        Librarian librarian = librarianRepository.findById(librarianId).orElseThrow(() -> new LibraryException(Message.LIBRARIAN_NOT_FOUND));
        LibrarianDepartment librarianDepartment = librarianDepartmentRepository.findByLibrarian(librarian);
        LibraryDepartment libraryDepartment = librarianDepartment.getLibraryDepartment();

        BookStorage bookStorage = new BookStorage();
        bookStorage.setBook(book);
        bookStorage.setLibraryDepartment(libraryDepartment);
        bookStorage.setTotalCount(bookCount);
        bookStorage.setAvailableCount(bookCount);
        bookStorageRepository.save(bookStorage);

        BookTransfer bookTransfer = new BookTransfer();
        bookTransfer.setBook(book);
        bookTransfer.setCount(bookCount);
        bookTransfer.setLibrarian(librarian);
        bookTransfer.setTo(libraryDepartment);
        bookTransfer.setComment(comment);
        bookTransfer.setRegisterDate(LocalDate.now());
        bookTransfer.setReason(reason);

        bookTransferRepository.save(bookTransfer);
    }

}
