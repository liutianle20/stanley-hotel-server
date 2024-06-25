package com.vincent.stanleyhotel.service;

import com.vincent.stanleyhotel.model.BookedRoom;

import java.util.List;

public interface IBookedRoomService {
    List<BookedRoom> getAllBookedRoomsById(Long id);
    
    String saveBookedRoom(Long roomId, BookedRoom bookedRoomRequest);

    void cancelBookedRoom(Long bookingId);

    BookedRoom findByBookedRoomConfirmationCode(String confirmationCode);

    List<BookedRoom> getAllBookedRooms();
}
