package ru.community.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.community.entity.BookTransfer;
import ru.community.service.BookTransferService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookTransferController {

    private final BookTransferService bookTransferService;

    @GetMapping("/book_transfer/{id}")
    public ResponseEntity<BookTransfer> getBookTransfer(@PathVariable int id){
        BookTransfer BT = bookTransferService.getBookTransfer(id);
        return ResponseEntity.ok(BT);
    }

    @GetMapping("/book_transfer/list")
    public ResponseEntity<List<BookTransfer>> getBookTransfer(){
        List<BookTransfer> list = bookTransferService.getAllGetBookTransfer();
        return ResponseEntity.ok(list);
    }
}
