package com.vincent.stanleyhotel.service;

import com.vincent.stanleyhotel.exception.InvalidBookingRequestException;
import com.vincent.stanleyhotel.model.BookedRoom;
import com.vincent.stanleyhotel.model.Room;
import com.vincent.stanleyhotel.repository.BookedRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookedRoomServiceImpl implements IBookedRoomService {

    private final BookedRoomRepository bookedRoomRepository;

    private final RoomServiceImpl roomService;

    @Override
    public List<BookedRoom> getAllBookedRooms() {
        return bookedRoomRepository.findAll();
    }

    @Override
    public List<BookedRoom> getAllBookedRoomsById(Long roomId) {
        return bookedRoomRepository.findByRoomId(roomId);
    }

    @Override
    public String saveBookedRoom(Long roomId, BookedRoom bookedRoomRequest) {
        if (bookedRoomRequest.getCheckOutDate().isBefore(bookedRoomRequest.getCheckInDate())) {
            throw new InvalidBookingRequestException("Check-out date must be after check-in date");
        }
        Room room = roomService.getRoomById(roomId).get();
        List<BookedRoom> existingBookedRooms = room.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookedRoomRequest, existingBookedRooms);
        if (roomIsAvailable) {
            room.addBookings(bookedRoomRequest);
            bookedRoomRepository.save(bookedRoomRequest);
        } else {
            throw new InvalidBookingRequestException("Sorry, there is no available booking for this room during the selected date ");
        }
        return bookedRoomRequest.getBookingConfirmationCode();
    }

    @Override
    public void cancelBookedRoom(Long bookingId) {
        bookedRoomRepository.deleteById(bookingId);
    }

    @Override
    public BookedRoom findByBookedRoomConfirmationCode(String confirmationCode) {
        return bookedRoomRepository.findByBookingConfirmationCode(confirmationCode);
    }

    private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                        || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                        || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                        && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                        || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
                        && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                        || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
                        && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))
                        || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                        && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))
                        || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                        && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );
    }
}
