package com.vincent.stanleyhotel.repository;

import com.vincent.stanleyhotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//extends JpaRepository<Room, Long>: This line indicates that RoomRepository extends JpaRepository. The <Room, Long> part specifies the type of the entity (Room) and the type of the primary key (Long).
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("select distinct r.roomType from Room r")
    List<String> findDistinctRoomTypes();
}
