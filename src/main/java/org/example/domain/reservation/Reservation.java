package org.example.domain.reservation;

import org.example.domain.guest.Guest;
import org.example.domain.reservation.dto.ReservationDTO;
import org.example.domain.room.Room;

import java.time.LocalDateTime;

public class Reservation {

    private final int id;
    private final Room room;
    private final Guest guest;
    private final LocalDateTime from;
    private final LocalDateTime to;

    public Reservation(int id, Room room, Guest guest, LocalDateTime from, LocalDateTime to) {
        this.id = id;
        this.room = room;
        this.guest = guest;
        this.from = from;
        this.to = to;
    }

    String toCSV() {
        return String.format("%s,%s,%s,%s,%s%s",
                this.id,
                room.getId(),
                guest.getId(),
                from.toString(),
                to.toString(),
                System.getProperty("line.separator"));
    }

    public int getId() {
        return this.id;
    }

    public ReservationDTO getAsDTO() {
        return new ReservationDTO(this.id, this.from,
                this.to, (int)this.room.getId(), this.room.getNumber(),
                this.guest.getId(), this.guest.getFirstName() + " " + this.guest.getLastName());
    }
}
