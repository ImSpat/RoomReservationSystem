package org.example.domain;

import org.example.domain.guest.GuestDatabaseRepository;
import org.example.domain.guest.GuestRepository;
import org.example.domain.guest.GuestService;
import org.example.domain.reservation.ReservationDatabaseRepository;
import org.example.domain.reservation.ReservationRepository;
import org.example.domain.reservation.ReservationService;
import org.example.domain.room.RoomDatabaseRepository;
import org.example.domain.room.RoomRepository;
import org.example.domain.room.RoomService;

public class ObjectPool {

    private final static RoomService roomService = new RoomService();
    private final static ReservationService reservationService = new ReservationService();

    private ObjectPool() {

    }

    public static GuestService getGuestService() {
        return GuestService.getInstance();
    }

    public static GuestRepository getGuestRepository() {
//        return GuestFileRepository.getInstance();
        return GuestDatabaseRepository.getInstance();
    }

    public static ReservationService getReservationService() {
//        return ReservationService.getInstance();
        return reservationService;
    }

    public static ReservationRepository getReservationRepository() {
        return ReservationDatabaseRepository.getInstance();
    }

    public static RoomService getRoomService() {
//        return RoomService.getInstance();
        return roomService;
    }

    public static RoomRepository getRoomRepository() {
//        return RoomFileRepository.getInstance();
        return RoomDatabaseRepository.getInstance();
    }
}
