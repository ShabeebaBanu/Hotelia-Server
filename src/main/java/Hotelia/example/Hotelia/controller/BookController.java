package Hotelia.example.Hotelia.controller;

import Hotelia.example.Hotelia.model.Book;
import Hotelia.example.Hotelia.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:5173"})
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/{userId}/{hotelId}/{roomId}")
    public ResponseEntity<Book> addBooking(@PathVariable Long userId,@PathVariable Long hotelId, @PathVariable Long roomId, @RequestBody Book bookRequest){
        return ResponseEntity.ok(bookService.addBooking(userId, hotelId, roomId, bookRequest));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Book>> getBookingByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(bookService.getBookByUserId(userId));
    }

    @GetMapping("/admin/{hotelId}")
    public ResponseEntity<List<Book>> getBookingByHotelId(@PathVariable Long hotelId){
        return ResponseEntity.ok(bookService.getBookByHotelId(hotelId));
    }

    @DeleteMapping("/admin/{bookingId}")
    public void deleteBooking(@PathVariable Long bookingId){
        bookService.deleteBooking(bookingId);
    }
}
