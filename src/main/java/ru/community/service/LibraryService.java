package ru.community.service;


import com.poiji.bind.Poiji;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.community.entity.*;
import ru.community.exception.LibrarianNotFound;
import ru.community.parser.MultipartToFile;
import ru.community.repository.*;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
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

    public Book addBooks(Book book, Map<String, String> param, int librarianId){
        Librarian librarian = repository.findById(librarianId).orElseThrow(LibrarianNotFound::new);
        LibrarianDepartment librarianDepartment = librarianDepartmentRepository.findByLibrarian(librarian);
        LibraryDepartment libraryDepartment = librarianDepartment.getLibraryDepartment();

        bookRepository.save(book);

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

    public List<Book> addBooksFromFile(MultipartFile file, int librarianId, String registerOfParish){
        File file1 = MultipartToFile.uploadToLocal(file);
        List<Book> books = bookRepository.saveAll(Poiji.fromExcel(file1, Book.class));

        for (Book book : books) {
            Librarian librarian = repository.findById(librarianId).orElseThrow(LibrarianNotFound::new);
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
