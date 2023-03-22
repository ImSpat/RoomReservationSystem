package org.example.domain.reservation;

import org.example.domain.guest.Guest;
import org.example.domain.guest.GuestService;
import org.example.domain.room.Room;
import org.example.domain.room.RoomService;
import org.example.util.Properties;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservationService {

    private final RoomService roomService = new RoomService();
    private final GuestService guestService = new GuestService();
    private final ReservationRepository repository = new ReservationRepository();

    public Reservation createNewReservation(LocalDate from, LocalDate to, int roomId, int guestId) {

        //TODO: handle null room
        Room room = roomService.getRoomById(roomId);
        //TODO: handle null guest
        Guest guest = guestService.getGuestById(guestId);

        LocalDateTime fromWithTime = from.atTime(Properties.HOTEL_NIGHT_START_HOUR,Properties.HOTEL_NIGHT_START_MINUTE);
        LocalDateTime toWithTime = to.atTime(Properties.HOTEL_NIGHT_END_HOUR, Properties.HOTEL_NIGHT_END_MINUTE);

        return repository.createNewReservation(room, guest, fromWithTime, toWithTime);
    }

    public void readAll() {
        repository.readAll();
    }

    public void saveAll() {
        repository.saveAll();
    }
}
