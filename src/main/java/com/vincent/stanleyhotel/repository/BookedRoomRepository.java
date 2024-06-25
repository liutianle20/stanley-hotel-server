package com.vincent.stanleyhotel.repository;

import com.vincent.stanleyhotel.model.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookedRoomRepository extends JpaRepository<BookedRoom, Long> {
    List<BookedRoom> findByRoomId(Long roomId);

    BookedRoom findByBookingConfirmationCode(String confirmationCode);
}
