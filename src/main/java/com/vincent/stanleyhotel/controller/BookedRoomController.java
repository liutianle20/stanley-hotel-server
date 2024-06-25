package com.vincent.stanleyhotel.controller;

import com.vincent.stanleyhotel.exception.InvalidBookingRequestException;
import com.vincent.stanleyhotel.exception.ResourceNotFoundException;
import com.vincent.stanleyhotel.model.BookedRoom;
import com.vincent.stanleyhotel.model.Room;
import com.vincent.stanleyhotel.response.BookingResponse;
import com.vincent.stanleyhotel.response.RoomResponse;
import com.vincent.stanleyhotel.service.BookedRoomServiceImpl;
import com.vincent.stanleyhotel.service.IBookedRoomService;
import com.vincent.stanleyhotel.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookedRoomController  {

    private final IBookedRoomService bookedRoomService;
    private final IRoomService roomService;

    @GetMapping("/all-bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookedRooms() {
        List<BookedRoom> bookedRooms = bookedRoomService.getAllBookedRooms();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (BookedRoom bookedRoom : bookedRooms) {
            BookingResponse bookingResponse = getBookingResponse(bookedRoom);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }

    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookedRoomByConfirmationCode(@PathVariable String confirmationCode) {
        try {
            BookedRoom bookedRoom = bookedRoomService.findByBookedRoomConfirmationCode(confirmationCode);
            BookingResponse bookingResponse = getBookingResponse(bookedRoom);
            return ResponseEntity.ok(bookingResponse);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/room/{roomId}/booking")
    public ResponseEntity<?> saveBookedRoom(@PathVariable Long roomId,
                                            @RequestBody BookedRoom bookedRoomRequest) {
        System.out.println(bookedRoomRequest);
        try {
            String confirmationCode = bookedRoomService.saveBookedRoom(roomId, bookedRoomRequest);
            return ResponseEntity.ok("Room booked successfully! Your confirmation code is: " + confirmationCode);
        } catch (InvalidBookingRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/booking/{bookingId}/delete")
    public void cancelBookedRoom(@PathVariable Long bookingId) {
        bookedRoomService.cancelBookedRoom(bookingId);
    }


    private BookingResponse getBookingResponse(BookedRoom bookedRoom) {
        Room theRoom = roomService.getRoomById(bookedRoom.getRoom().getId()).get();
        RoomResponse roomResponse = new RoomResponse(
                theRoom.getId(),
                theRoom.getRoomType(),
                theRoom.getRoomPrice());
        return new BookingResponse(
                bookedRoom.getBookingId(),
                bookedRoom.getCheckInDate(),
                bookedRoom.getCheckOutDate(),
                bookedRoom.getGuestFullName(),
                bookedRoom.getGuestEmail(),
                bookedRoom.getNumOfAdults(),
                bookedRoom.getNumOfChildren(),
                bookedRoom.getTotalNumberOfGuests(),
                bookedRoom.getBookingConfirmationCode(),
                roomResponse);
    }

}
