package org.example.domain.reservation;

import org.example.domain.ObjectPool;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationServiceTest {

    @Test
    public void createNewReservationShouldThrowExceptionWithNotExistingRoomId() {

        //given
        ReservationService reservationService = ObjectPool.getReservationService();
        LocalDate from = LocalDate.parse("2020-11-30");
        LocalDate to = LocalDate.parse("2020-12-03");
        Long roomId = 1l;
        Long guestId = 1l;

        //then
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> reservationService.createNewReservation(from, to, roomId, guestId));

        String expectedMessage = "Invalid room ID: " + roomId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}