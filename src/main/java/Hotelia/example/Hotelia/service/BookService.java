package Hotelia.example.Hotelia.service;

import Hotelia.example.Hotelia.model.Book;
import Hotelia.example.Hotelia.model.Room;
import Hotelia.example.Hotelia.model.User;
import Hotelia.example.Hotelia.repository.BookRepository;
import Hotelia.example.Hotelia.repository.RoomRepository;
import Hotelia.example.Hotelia.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository, RoomRepository roomRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    public Book addBooking(Long userId, Long roomId, Book bookRequest){

        Room room = roomRepository.findById(roomId)
                .orElseThrow(()->new RuntimeException("Room with ID " + roomId + "not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User with ID " + userId + "not found"));

        if ((room != null && user != null)){

            room.setStatus("Booked");
            roomRepository.save(room);

            bookRequest.setRoom(room);
            bookRequest.setUser(user);
        }

        Book newBooking = new Book();
        newBooking = bookRepository.save(bookRequest);

        return bookRequest;

    }


}
