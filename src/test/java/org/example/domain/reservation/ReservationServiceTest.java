package org.example.domain.reservation;

import org.example.domain.ObjectPool;
import org.example.domain.guest.GuestService;
import org.example.domain.room.BedType;
import org.example.domain.room.Room;
import org.example.domain.room.RoomService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReservationServiceTest {

    @Test
    public void createNewReservationShouldThrowExceptionWithNotExistingRoomId() {

        //given
        LocalDate from = LocalDate.parse("2020-11-30");
        LocalDate to = LocalDate.parse("2020-12-03");
        Long roomId = 1l;
        Long guestId = 1l;

        ReservationService reservationService = new ReservationService();
        RoomService roomService = mock(RoomService.class);
        reservationService.setRoomService(roomService);
        when(roomService.getRoomById(roomId)).thenReturn(null);

        //when, then
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> reservationService.createNewReservation(from, to, roomId, guestId));

        String expectedMessage = "Invalid room ID: " + roomId;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createNewReservationShouldThrowExceptionWithNotExistingGuestId() {

        //given
        LocalDate from = LocalDate.parse("2020-11-30");
        LocalDate to = LocalDate.parse("2020-12-03");
        Long roomId = 1l;
        Long guestId = 1l;

        ReservationService reservationService = new ReservationService();
        RoomService roomService = mock(RoomService.class);
        GuestService guestService = mock(GuestService.class);
        reservationService.setRoomService(roomService);
        reservationService.setGuestService(guestService);
        when(roomService.getRoomById(roomId)).thenReturn(new Room(1, 333, Arrays.asList(BedType.values())));
        when(guestService.getGuestById(guestId)).thenReturn(null);

        //when, then
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> reservationService.createNewReservation(from, to, roomId, guestId));

        String expectedMessage = "Invalid guest ID: " + guestId;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}