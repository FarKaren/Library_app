package ru.community.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.community.entity.*;
import ru.community.exception.LibrarianNotFound;
import ru.community.repository.*;
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


    public void addLibrarian(Librarian librarian){
        repository.save(librarian);
    }

    public Librarian getLibrarian(int librarianId){
        return repository.findById(librarianId).orElseThrow(LibrarianNotFound::new);
    }

    public List<Librarian> getAllLibrarian(){
       return repository.findAll();
    }

    public void deleteLibrarian(int id){
        Librarian librarian = repository.findById(id).orElseThrow(LibrarianNotFound::new);
        repository.delete(librarian);
    }

    public Book addBooks(Book book, int bookCount, String causeOfParish, int librarianId, String comment) throws Exception {
        Librarian librarian = repository.findById(librarianId).orElseThrow(LibrarianNotFound::new);
        LibrarianDepartment librarianDepartment = librarianDepartmentRepository.findByLibrarian(librarian);
        LibraryDepartment libraryDepartment = librarianDepartment.getLibraryDepartment();

        bookRepository.save(book);

        BookStorage bookStorage = new BookStorage();
        bookStorage.setBook(book);
        bookStorage.setDepartmentId(libraryDepartment.getId());
        bookStorage.setTotalCount(bookCount);
        bookStorage.setAvailableCount(bookCount);
        bookStorageRepository.save(bookStorage);

        BookTransfer bookTransfer = new BookTransfer();
        bookTransfer.setBookStorage(bookStorage);
        bookTransfer.setLibrarian(librarian);
        bookTransfer.setComment(comment);
        bookTransfer.setRegisterDate(LocalDate.now());

        for (CauseOfParish c : CauseOfParish.values())
            if (c.getDescription().equals(causeOfParish))
                bookTransfer.setCauseOfParish(c);

        if(bookTransfer.getCauseOfParish() == null)
            throw new Exception("causeOfParish is NULL");

            bookTransferRepository.save(bookTransfer);

            return book;
    }

//    public List<Book> addBooksFromFile(MultipartFile file, int librarianId, String causeOfParish, String comment) throws Exception {
//        String path = MultipartFileToFile.uploadToLocal(file);
//        List<Book> books = CSVParser.csvParser(path);
//        //List<Book> books = bookRepository.saveAll(Poiji.fromExcel(file1, Book.class));
//        //List<Book> books = Poiji.fromExcel(file1, Book.class);
//        System.out.println(books);
//        bookRepository.saveAll(books);
//        Librarian librarian = repository.findById(librarianId).orElseThrow(LibrarianNotFound::new);
//        LibrarianDepartment librarianDepartment = librarianDepartmentRepository.findByLibrarian(librarian);
//        LibraryDepartment libraryDepartment = librarianDepartment.getLibraryDepartment();
//
//        for (Book book : books) {
//
//            BookStorage bookStorage = new BookStorage();
//            bookStorage.setBook(book);
//            bookStorage.setDepartmentId(libraryDepartment.getId());
//            bookStorage.setTotalCount(books.size());
//            bookStorage.setAvailableCount(books.size());
//            bookStorageRepository.save(bookStorage);
//
//            BookTransfer bookTransfer = new BookTransfer();
//            bookTransfer.setBookStorage(bookStorage);
//            bookTransfer.setLibrarian(librarian);
//            bookTransfer.setComment(comment);
//            bookTransfer.setRegisterDate(LocalDate.now());
//
//            for (CauseOfParish c : CauseOfParish.values()) {
//                if(c.name().equals(causeOfParish))
//                    bookTransfer.setCauseOfParish(c);
//                else throw new Exception();
//            }
//            bookTransferRepository.save(bookTransfer);
//        }
//        return books;
//    }
}
