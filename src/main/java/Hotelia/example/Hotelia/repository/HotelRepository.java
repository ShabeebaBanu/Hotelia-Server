package Hotelia.example.Hotelia.repository;

import Hotelia.example.Hotelia.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Optional<Hotel> findByCode(String code);

    Optional<Hotel> findById(Long hotelId);

    @Query(
            "select h from Hotel h " +
                    "where (:code is null or :code = '' or h.code = :code)" +
                    "and (:name is null or :name = '' or h.name like %:name%)" +
                    "and (:province is null or :province = '' or h.province = :province)" +
                    "and (:district is null or :district = '' or h.district = :district)"
    )
    List<Hotel> findByFilter(
            String code,
            String name,
            String province,
            String district
    );

    void deleteById(Long id);

    List<Hotel> findByUserId(Long userId);

}
