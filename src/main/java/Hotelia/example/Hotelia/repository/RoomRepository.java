package Hotelia.example.Hotelia.repository;

import Hotelia.example.Hotelia.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findById(Long roomId);

    void deleteById(Long roomId);

    List<Room> findByHotelId(Long hotelId);

}
