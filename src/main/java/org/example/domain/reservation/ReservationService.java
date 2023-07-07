package org.example.domain.reservation;

import org.example.domain.ObjectPool;
import org.example.domain.guest.Guest;
import org.example.domain.guest.GuestService;
import org.example.domain.reservation.dto.ReservationDTO;
import org.example.domain.room.Room;
import org.example.domain.room.RoomService;
import org.example.util.SystemUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {

    private RoomService roomService = ObjectPool.getRoomService();
    private GuestService guestService = ObjectPool.getGuestService();
    private final ReservationRepository repository = ObjectPool.getReservationRepository();
    //private static final ReservationService instance = new ReservationService();
    // removed Singleton because test were failing
    public ReservationService() {
    }

//    public static ReservationService getInstance() {
//        return instance;
//    }

    public Reservation createNewReservation(LocalDate from, LocalDate to, long roomId, long guestId) throws IllegalArgumentException {

        Room room = roomService.getRoomById(roomId);
        if (room == null) {
            throw new IllegalArgumentException("Invalid room ID: " + roomId);
        }

        Guest guest = guestService.getGuestById(guestId);
        if (guest == null) {
            throw new IllegalArgumentException("Invalid guest ID: " + guestId);
        }

        LocalDateTime fromWithTime = from.atTime(SystemUtils.HOTEL_NIGHT_START_HOUR, SystemUtils.HOTEL_NIGHT_START_MINUTE);
        LocalDateTime toWithTime = to.atTime(SystemUtils.HOTEL_NIGHT_END_HOUR, SystemUtils.HOTEL_NIGHT_END_MINUTE);

        if (toWithTime.isBefore(fromWithTime)) {
            throw new IllegalArgumentException();
        }

        return repository.createNewReservation(room, guest, fromWithTime, toWithTime);
    }

    public void readAll() {
        repository.readAll();
    }

    public void saveAll() {
        repository.saveAll();
    }

    public List<ReservationDTO> getReservationsAsDTO() {
        List<ReservationDTO> result = new ArrayList<>();
        List<Reservation> reservations = repository.getAll();

        for (Reservation reservation : reservations) {
            ReservationDTO dto = reservation.getAsDTO();
            result.add(dto);
        }
        return result;
    }

    public void removeReservation(long id) {
        this.repository.remove(id);
    }

    public List<Reservation> getAllReservations() {
        return this.repository.getAll();
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    public void setGuestService(GuestService guestService) {
        this.guestService = guestService;
    }
}
