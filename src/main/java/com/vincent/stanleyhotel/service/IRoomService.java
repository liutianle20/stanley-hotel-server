package com.vincent.stanleyhotel.service;

import com.vincent.stanleyhotel.model.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IRoomService {
    Room addRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws SQLException, IOException;

    List<String> getAllRoomTypes();

    List<Room> getAllRooms();

    byte[] getRoomPhotoById(Long id) throws SQLException;

    void deleteRoom(Long id);

    Room updateRoom(Long id, String roomType, BigDecimal roomPrice, byte[] photoBytes);

    Optional<Room> getRoomById(Long id);
}
