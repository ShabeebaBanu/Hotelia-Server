package Hotelia.example.Hotelia.repository;

import Hotelia.example.Hotelia.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findById(Long bookId);

    @Query("select b.room.id from Book b where "+
                    "(b.checkIn <= :checkOut and b.checkOut >= :checkIn)")
    List<Long> findBookedRoomIds(@Param("checkIn")Date checkIn, @Param("checkOut")Date checkOut);

   List<Book> findByUserId(Long userId);

    List<Book> findByHotelId(Long hotelId);



}
