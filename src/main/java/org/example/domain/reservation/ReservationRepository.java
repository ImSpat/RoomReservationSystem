package org.example.domain.reservation;

import org.example.domain.guest.Guest;
import org.example.domain.room.Room;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository {
    Reservation createNewReservation(Room room, Guest guest, LocalDateTime from, LocalDateTime to);

    List<Reservation> getAll();

    void readAll();

    void saveAll();

    void remove(long id);
}
