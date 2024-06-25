package com.vincent.stanleyhotel.service;

import com.vincent.stanleyhotel.exception.InternalServiceException;
import com.vincent.stanleyhotel.exception.ResourceNotFoundException;
import com.vincent.stanleyhotel.model.Room;
import com.vincent.stanleyhotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements IRoomService {

    private final RoomRepository roomRepository;

    @Override
    public Room addRoom(MultipartFile file, String roomType, BigDecimal roomPrice) throws SQLException, IOException {
        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        if (!file.isEmpty()) {
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            room.setPhoto(photoBlob);
        }
        return roomRepository.save(room) ; // `save` method is used to create a new entity or update an existing one.
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll(); // returns a collection of all the entities that match the specified type
    }

    @Override
    public byte[] getRoomPhotoById(Long id) throws SQLException {
        Optional<Room> theRoom = roomRepository.findById(id); //It is designed to address the problem of null pointer exceptions by providing a way to express that a method may not return a value.
        if (theRoom.isEmpty()) {
            throw new ResourceNotFoundException("Room not found");
        }
        Blob photoBlob = theRoom.get().getPhoto();
        if (photoBlob != null) {
            return photoBlob.getBytes(1, (int) photoBlob.length());
        }
        return null;
    }

    @Override
    public void deleteRoom(Long id) {
        Optional<Room> theRoom = roomRepository.findById(id);
        if (theRoom.isPresent()) {
            roomRepository.deleteById(id);
        }
    }

    @Override
    public Room updateRoom(Long id, String roomType, BigDecimal roomPrice, byte[] photoBytes) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        if (roomType != null && !roomType.isEmpty()) room.setRoomType(roomType);
        if (roomPrice != null) room.setRoomPrice(roomPrice);
        if (photoBytes != null && photoBytes.length > 0) {
            try {
                room.setPhoto(new SerialBlob(photoBytes));
            } catch (SQLException ex) {
                throw new InternalServiceException("Error updating photo");
            }
        }
        return roomRepository.save(room);
    }

    @Override
    public Optional<Room> getRoomById(Long id) {
        return Optional.of(roomRepository.findById(id).get());
    }
}
