package Hotelia.example.Hotelia.controller;

import Hotelia.example.Hotelia.exception.HotelAlreadyExistException;
import Hotelia.example.Hotelia.model.Hotel;
import Hotelia.example.Hotelia.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/hotel")
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:5173"})
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping("/admin")
    public ResponseEntity<Hotel> addHotel(@RequestBody Hotel hotelRequest) throws HotelAlreadyExistException {
        return ResponseEntity.ok(hotelService.addHotel(hotelRequest));
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotelByHotelId(@PathVariable Long hotelId){
        return ResponseEntity.ok(hotelService.getHotelByID(hotelId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Hotel>> searchHotel(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String province,
            @RequestParam(required = false) String district
    ){
        List<Hotel> hotels = hotelService.searchHotel(
                Optional.ofNullable(code),
                Optional.ofNullable(name),
                Optional.ofNullable(province),
                Optional.ofNullable(district)
        );
        return ResponseEntity.ok(hotels);
    }

    @DeleteMapping("/admin/remove/{hotelId}")
    public void deleteHotelById(@PathVariable Long hotelId){
         hotelService.deleteHotelById(hotelId);
    }

    @PostMapping("/admin/update/{hotelId}")
    public ResponseEntity<Hotel> updateHotel(
            @PathVariable Long hotelId,
            @RequestBody Hotel updatedHotel
    ){
        Hotel updated = hotelService.updateHotel(hotelId, updatedHotel);
        return ResponseEntity.ok(updated);
    }
}
