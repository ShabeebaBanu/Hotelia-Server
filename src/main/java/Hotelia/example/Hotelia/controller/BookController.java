package Hotelia.example.Hotelia.controller;

import Hotelia.example.Hotelia.model.Book;
import Hotelia.example.Hotelia.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:5173"})
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/{userId}/{roomId}")
    public ResponseEntity<Book> addBooking(@PathVariable Long userId, @PathVariable Long roomId, @RequestBody Book bookRequest){
        return ResponseEntity.ok(bookService.addBooking(userId, roomId, bookRequest));
    }
}
