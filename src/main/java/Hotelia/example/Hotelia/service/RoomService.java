package Hotelia.example.Hotelia.service;

import Hotelia.example.Hotelia.model.Hotel;
import Hotelia.example.Hotelia.model.Room;
import Hotelia.example.Hotelia.repository.BookRepository;
import Hotelia.example.Hotelia.repository.HotelRepository;
import Hotelia.example.Hotelia.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final BookRepository bookRepository;

    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository, BookRepository bookRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.bookRepository = bookRepository;
    }

    public Room addRoom(Long hotelId, Room roomRequest){

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel with ID " + hotelId + " not found."));

        roomRequest.setHotel(hotel);
        Room newRoom = new Room();
        newRoom = roomRepository.save(roomRequest);

        return newRoom;
    }

    public void deleteRoom(Long roomId){
        roomRepository.deleteById(roomId);
    }

    public List<Room> findRoomsAvailableForBooking(Date checkIn, Date checkOut){
        List<Long> bookedRoomIds = bookRepository.findBookedRoomIds(checkIn, checkOut) ;
        List<Room> allRooms = roomRepository.findAll();

        return allRooms.stream()
                .filter(room -> !bookedRoomIds.contains(room.getId()) && "available".equalsIgnoreCase(room.getStatus()))
                .collect(Collectors.toList());
    }

    public List<Room> findRoomByHotelId(Long hotelId){
        return roomRepository.findByHotelId(hotelId);
    }
}
