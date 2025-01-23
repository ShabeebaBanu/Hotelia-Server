package Hotelia.example.Hotelia.controller;

import Hotelia.example.Hotelia.model.Room;
import Hotelia.example.Hotelia.service.HotelService;
import Hotelia.example.Hotelia.service.RoomService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:5173"})
@RequestMapping("/api/v1/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;

    }

    @PostMapping("/admin/{hotelId}")
    public ResponseEntity<Room> addRoom(@PathVariable Long hotelId, @RequestBody Room roomRequest) {
        Room savedRoom = new Room();
        savedRoom = roomService.addRoom(hotelId, roomRequest);

        return ResponseEntity.ok(savedRoom);
    }

    @DeleteMapping("/admin/remove/{roomId}")
    public void deleteRoom(@PathVariable Long roomId){
        roomService.deleteRoom(roomId);
    }


    @GetMapping("/available")
    public ResponseEntity<List<Room>> getAvailableRooms(
            @RequestParam @DateTimeFormat(pattern = "yyyy-mm-dd") Date checkIn,
            @RequestParam @DateTimeFormat(pattern = "yyyy-mm-dd") Date checkOut){
        List<Room> availableRooms = roomService.findRoomsAvailableForBooking(checkIn, checkOut);
        return ResponseEntity.ok(availableRooms);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<List<Room>> getRoomsByHotelId(@PathVariable Long hotelId){
        return ResponseEntity.ok(roomService.findRoomByHotelId(hotelId));
    }
}
