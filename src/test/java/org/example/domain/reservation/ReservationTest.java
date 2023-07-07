package org.example.domain.reservation;

import org.example.domain.guest.Gender;
import org.example.domain.guest.Guest;
import org.example.domain.guest.dto.GuestDTO;
import org.example.domain.reservation.dto.ReservationDTO;
import org.example.domain.room.BedType;
import org.example.domain.room.Room;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationTest {

    @Test
    public void toCSVTest(){

        //given
        List<BedType> beds = Arrays.asList(BedType.values());
        Room room = new Room(1, 302, beds);
        Guest guest = new Guest(1, "Jan", "Kowalski", 30, Gender.MALE);
        LocalDateTime from = LocalDateTime.parse("2023-12-01T15:00");
        LocalDateTime to = LocalDateTime.parse("2023-12-10T10:00");

        Reservation reservation = new Reservation(1, room, guest, from, to);

        //when
        String csvCreated = reservation.toCSV();

        //then
        String csvTemplate = "1,1,1,2023-12-01T15:00,2023-12-10T10:00"+System.getProperty("line.separator");
        assertEquals(csvTemplate, csvCreated, "Porównanie CSV");
    }

    @Test
    public void toCSVWithNullBedListAndGenderTest(){

        //given
        Room room = new Room(1, 302, null);
        Guest guest = new Guest(1, "Jan", "Kowalski", 30, null);
        LocalDateTime from = LocalDateTime.parse("2023-12-01T15:00");
        LocalDateTime to = LocalDateTime.parse("2023-12-10T10:00");

        Reservation reservation = new Reservation(1, room, guest, from, to);

        //when
        String csvCreated = reservation.toCSV();

        //then
        String csvTemplate = "1,1,1,2023-12-01T15:00,2023-12-10T10:00"+System.getProperty("line.separator");
        assertEquals(csvTemplate, csvCreated, "Porównanie CSV");

    }

    @Test
    public void toDTOTest(){

        //given
        List<BedType> beds = Arrays.asList(BedType.values());
        Room room = new Room(1, 302, beds);
        Guest guest = new Guest(1, "Jan", "Kowalski", 30, Gender.MALE);
        LocalDateTime from = LocalDateTime.parse("2023-12-01T15:00");
        LocalDateTime to = LocalDateTime.parse("2023-12-10T10:00");

        Reservation reservation = new Reservation(1, room, guest, from, to);

        //when
        ReservationDTO reservationDTO = reservation.getAsDTO();

        //then
        assertEquals(reservationDTO.getId(),1);
        assertEquals(reservationDTO.getFrom(),from);
        assertEquals(reservationDTO.getTo(),to);
        assertEquals(reservationDTO.getRoomId(),1);
        assertEquals(reservationDTO.getRoomNumber(),302);
        assertEquals(reservationDTO.getGuestId(),1);
        assertEquals(reservationDTO.getGuestName(),"Jan Kowalski");
    }
}
