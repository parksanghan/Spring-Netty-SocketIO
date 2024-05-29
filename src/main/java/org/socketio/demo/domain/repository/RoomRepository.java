package org.socketio.demo.domain.repository;

import org.socketio.demo.domain.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository  extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomName(String roomName);
}
