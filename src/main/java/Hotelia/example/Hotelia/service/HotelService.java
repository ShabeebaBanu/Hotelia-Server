package Hotelia.example.Hotelia.service;

import Hotelia.example.Hotelia.exception.HotelAlreadyExistException;
import Hotelia.example.Hotelia.model.Hotel;
import Hotelia.example.Hotelia.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {
     private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    //Add New Hotel
    public Hotel addHotel(Hotel hotelRequest) throws HotelAlreadyExistException {
        if(hotelRepository.findByCode(hotelRequest.getCode()).isPresent()){
             throw new HotelAlreadyExistException("Hotel Already Exist");
        }

        Hotel newHotel = new Hotel();
        newHotel = hotelRepository.save(hotelRequest);

        return newHotel;
    }

    //Get Hotel By HotelId
    public Hotel getHotelByID(Long hotelId){
        return hotelRepository.findById(hotelId).orElse(null);
    }

    public List<Hotel> searchHotel(
            Optional<String> code,
            Optional<String> name,
            Optional<String> province,
            Optional<String> district
    ){
        return hotelRepository.findByFilter(
                code.orElse(""),
                name.orElse(""),
                province.orElse(""),
                district.orElse("")
        );
    }

    public void deleteHotelById(Long hotelId){
        hotelRepository.deleteById(hotelId);
    }

    public Hotel updateHotel(Long hotelId, Hotel updatedHotel){
        Hotel existingHotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel with Id " + hotelId + "not found"));

        existingHotel.setName(updatedHotel.getName());
        existingHotel.setCode(updatedHotel.getCode());
        existingHotel.setAddNo(updatedHotel.getAddNo());
        existingHotel.setAddStreet(updatedHotel.getAddStreet());
        existingHotel.setAddCity(updatedHotel.getAddCity());
        existingHotel.setProvince(updatedHotel.getProvince());
        existingHotel.setDistrict(updatedHotel.getDistrict());
        existingHotel.setPhone(updatedHotel.getPhone());
        existingHotel.setImagePath(updatedHotel.getImagePath());

        return hotelRepository.save(existingHotel);
    }
}
