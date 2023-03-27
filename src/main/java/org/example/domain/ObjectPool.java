package org.example.domain;

import org.example.domain.guest.GuestRepository;
import org.example.domain.guest.GuestService;
import org.example.domain.reservation.ReservationRepository;
import org.example.domain.reservation.ReservationService;
import org.example.domain.room.RoomDatabaseRepository;
import org.example.domain.room.RoomFileRepository;
import org.example.domain.room.RoomRepository;
import org.example.domain.room.RoomService;

public class ObjectPool {

    private ObjectPool() {

    }

    public static GuestService getGuestService() {
        return GuestService.getInstance();
    }

    public static GuestRepository getGuestRepository() {
        return GuestRepository.getInstance();
    }

    public static ReservationService getReservationService() {
        return ReservationService.getInstance();
    }

    public static ReservationRepository getReservationRepository() {
        return ReservationRepository.getInstance();
    }

    public static RoomService getRoomService() {
        return RoomService.getInstance();
    }

    public static RoomRepository getRoomRepository() {
//        return RoomFileRepository.getInstance();
        return RoomDatabaseRepository.getInstance();
    }
}
