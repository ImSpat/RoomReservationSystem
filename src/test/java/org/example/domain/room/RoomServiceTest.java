package org.example.domain.room;

import org.example.domain.ObjectPool;
import org.example.domain.guest.Gender;
import org.example.domain.guest.Guest;
import org.example.domain.reservation.Reservation;
import org.example.domain.reservation.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RoomServiceTest {

    @Test
    public void convertFromIntOptionsIntoBedTypesTest() {
        //given
        RoomService roomService = ObjectPool.getRoomService();
        int[] bedTypeOptions = new int[]{1, 2, 3};
        //when
        List<BedType> bedTypes = roomService.getBedTypes(bedTypeOptions);
        //then
        assertEquals(3, bedTypes.size());
        assertEquals(BedType.SINGLE, bedTypes.get(0));
        assertEquals(BedType.DOUBLE, bedTypes.get(1));
        assertEquals(BedType.KING_SIZE, bedTypes.get(2));
    }

    @Test
    public void getAvailableRoomsShouldThrowExceptionWithNullParam(){

        //given
        RoomService roomService = new RoomService();

        //then
        assertThrows(IllegalArgumentException.class,
                () -> roomService.getAvailableRooms(null, null));
    }

    @Test
    public void getAvailableRoomsShouldThrowExceptionWhenToDateIsBeforeFrom() {

        //given
        RoomService roomService = new RoomService();
        LocalDate from = LocalDate.parse("2020-12-03");
        LocalDate to = LocalDate.parse("2020-11-30");

        //then
        assertThrows(IllegalArgumentException.class,
                () -> roomService.getAvailableRooms(from, to));
    }

    @Test
    public void getAvailableRoomsShouldReturnEmptyListWhenNoRoomsInSystem(){

        //given
        RoomRepository roomRepository = mock(RoomRepository.class);
        when(roomRepository.getAll()).thenReturn(new ArrayList<>());
        RoomService roomService = new RoomService();
        roomService.setRepository(roomRepository);

        LocalDate from = LocalDate.parse("2020-11-30");
        LocalDate to = LocalDate.parse("2020-12-03");

        //when
        List<Room> availableRooms = roomService.getAvailableRooms(from, to);

        //then
        assertEquals(0, availableRooms.size());
    }

    @Test
    public void getAvailableRoomsShouldReturnAllRoomsWhenNoReservation(){

        //given
        RoomRepository roomRepository = mock(RoomRepository.class);
        Room room = new Room(1, 302, null);
        List<Room> rooms = new ArrayList<>();
        rooms.add(room);
        when(roomRepository.getAll()).thenReturn(rooms);

        ReservationService reservationService = mock(ReservationService.class);
        when(reservationService.getAllReservations()).thenReturn(new ArrayList<Reservation>());

        RoomService roomService = new RoomService();
        roomService.setRepository(roomRepository);
        roomService.setReservationService(reservationService);

        LocalDate from = LocalDate.parse("2020-11-30");
        LocalDate to = LocalDate.parse("2020-12-03");

        //when
        List<Room> availableRooms = roomService.getAvailableRooms(from, to);

        //then
        assertEquals(1, availableRooms.size());
    }

    @Test
    public void getAvailableRoomsShouldRemoveRoomIfReservationStartDateIsTheSame(){

        //given
        RoomRepository roomRepository = mock(RoomRepository.class);
        Room reservedRoom = new Room(1, 302, null);
        Room availableRoom = new Room(2, 303, null);
        List<Room> rooms = new ArrayList<>();
        rooms.add(reservedRoom);
        rooms.add(availableRoom);
        when(roomRepository.getAll()).thenReturn(rooms);

        Guest guest = new Guest(1, "Jan", "Kowalski", 33, Gender.MALE);

        LocalDateTime fromReserved = LocalDateTime.parse("2023-11-30T15:00");
        LocalDateTime toReserved = LocalDateTime.parse("2023-12-01T09:00");

        Reservation reservation = new Reservation(1, reservedRoom, guest, fromReserved, toReserved);

        ReservationService reservationService = mock(ReservationService.class);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        when(reservationService.getAllReservations()).thenReturn(reservations);

        RoomService roomService = new RoomService();
        roomService.setRepository(roomRepository);
        roomService.setReservationService(reservationService);

        LocalDate from = LocalDate.parse("2023-11-30");
        LocalDate to = LocalDate.parse("2023-12-03");

        //when
        List<Room> availableRooms = roomService.getAvailableRooms(from, to);

        //then
        assertEquals(1, availableRooms.size());
        assertEquals(303, availableRooms.get(0).getNumber());
    }

    @Test
    public void getAvailableRoomsShouldRemoveRoomIfReservationEndDateIsTheSame(){

        //given
        RoomRepository roomRepository = mock(RoomRepository.class);
        Room reservedRoom = new Room(1, 302, null);
        Room availableRoom = new Room(2, 303, null);
        List<Room> rooms = new ArrayList<>();
        rooms.add(reservedRoom);
        rooms.add(availableRoom);
        when(roomRepository.getAll()).thenReturn(rooms);

        Guest guest = new Guest(1, "Jan", "Kowalski", 33, Gender.MALE);

        LocalDateTime fromReserved = LocalDateTime.parse("2023-11-30T15:00");
        LocalDateTime toReserved = LocalDateTime.parse("2023-12-01T10:00");

        Reservation reservation = new Reservation(1, reservedRoom, guest, fromReserved, toReserved);

        ReservationService reservationService = mock(ReservationService.class);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        when(reservationService.getAllReservations()).thenReturn(reservations);

        RoomService roomService = new RoomService();
        roomService.setRepository(roomRepository);
        roomService.setReservationService(reservationService);

        LocalDate from = LocalDate.parse("2023-11-10");
        LocalDate to = LocalDate.parse("2023-12-01");

        //when
        List<Room> availableRooms = roomService.getAvailableRooms(from, to);

        //then
        assertEquals(1, availableRooms.size());
        assertEquals(303, availableRooms.get(0).getNumber());
    }

    @Test
    public void getAvailableRoomsShouldRemoveRoomIfReservationStartDateIsBetweenLookedUpDates(){

        //given
        RoomRepository roomRepository = mock(RoomRepository.class);
        Room reservedRoom = new Room(1, 302, null);
        Room availableRoom = new Room(2, 303, null);
        List<Room> rooms = new ArrayList<>();
        rooms.add(reservedRoom);
        rooms.add(availableRoom);
        when(roomRepository.getAll()).thenReturn(rooms);

        Guest guest = new Guest(1, "Jan", "Kowalski", 33, Gender.MALE);

        LocalDateTime fromReserved = LocalDateTime.parse("2023-12-01T15:00");
        LocalDateTime toReserved = LocalDateTime.parse("2023-12-02T10:00");

        Reservation reservation = new Reservation(1, reservedRoom, guest, fromReserved, toReserved);

        ReservationService reservationService = mock(ReservationService.class);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        when(reservationService.getAllReservations()).thenReturn(reservations);

        RoomService roomService = new RoomService();
        roomService.setRepository(roomRepository);
        roomService.setReservationService(reservationService);

        LocalDate from = LocalDate.parse("2023-11-20");
        LocalDate to = LocalDate.parse("2023-12-05");

        //when
        List<Room> availableRooms = roomService.getAvailableRooms(from, to);

        //then
        assertEquals(1, availableRooms.size());
        assertEquals(303, availableRooms.get(0).getNumber());
    }

    @Test
    public void getAvailableRoomsShouldRemoveRoomIfReservationEndDateIsBetweenLookedUpDates(){

        //given
        RoomRepository roomRepository = mock(RoomRepository.class);
        Room reservedRoom = new Room(1, 302, null);
        Room availableRoom = new Room(2, 303, null);
        List<Room> rooms = new ArrayList<>();
        rooms.add(reservedRoom);
        rooms.add(availableRoom);
        when(roomRepository.getAll()).thenReturn(rooms);

        Guest guest = new Guest(1, "Jan", "Kowalski", 33, Gender.MALE);

        LocalDateTime fromReserved = LocalDateTime.parse("2023-12-01T15:00");
        LocalDateTime toReserved = LocalDateTime.parse("2023-12-05T10:00");

        Reservation reservation = new Reservation(1, reservedRoom, guest, fromReserved, toReserved);

        ReservationService reservationService = mock(ReservationService.class);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        when(reservationService.getAllReservations()).thenReturn(reservations);

        RoomService roomService = new RoomService();
        roomService.setRepository(roomRepository);
        roomService.setReservationService(reservationService);

        LocalDate from = LocalDate.parse("2023-12-04");
        LocalDate to = LocalDate.parse("2023-12-06");

        //when
        List<Room> availableRooms = roomService.getAvailableRooms(from, to);

        //then
        assertEquals(1, availableRooms.size());
        assertEquals(303, availableRooms.get(0).getNumber());
    }

    @Test
    public void getAvailableRoomsShouldRemoveRoomIfLookingBetweenExistingReservation(){

        //given
        RoomRepository roomRepository = mock(RoomRepository.class);
        Room reservedRoom = new Room(1, 302, null);
        Room availableRoom = new Room(2, 303, null);
        List<Room> rooms = new ArrayList<>();
        rooms.add(reservedRoom);
        rooms.add(availableRoom);
        when(roomRepository.getAll()).thenReturn(rooms);

        Guest guest = new Guest(1, "Jan", "Kowalski", 33, Gender.MALE);

        LocalDateTime fromReserved = LocalDateTime.parse("2023-12-01T15:00");
        LocalDateTime toReserved = LocalDateTime.parse("2023-12-10T10:00");

        Reservation reservation = new Reservation(1, reservedRoom, guest, fromReserved, toReserved);

        ReservationService reservationService = mock(ReservationService.class);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        when(reservationService.getAllReservations()).thenReturn(reservations);

        RoomService roomService = new RoomService();
        roomService.setRepository(roomRepository);
        roomService.setReservationService(reservationService);

        LocalDate from = LocalDate.parse("2023-12-04");
        LocalDate to = LocalDate.parse("2023-12-06");

        //when
        List<Room> availableRooms = roomService.getAvailableRooms(from, to);

        //then
        assertEquals(1, availableRooms.size());
        assertEquals(303, availableRooms.get(0).getNumber());
    }

    @Test
    public void getAvailableRoomsTest(){

        //given
        RoomRepository roomRepository = mock(RoomRepository.class);
        Room reservedRoom = new Room(1, 302, null);
        Room availableRoom = new Room(2, 303, null);
        Room room = new Room(3, 304, null);
        List<Room> rooms = new ArrayList<>();
        rooms.add(reservedRoom);
        rooms.add(availableRoom);
        rooms.add(room);
        when(roomRepository.getAll()).thenReturn(rooms);

        Guest guest = new Guest(1, "Jan", "Kowalski", 33, Gender.MALE);

        LocalDateTime fromReserved = LocalDateTime.parse("2023-12-01T15:00");
        LocalDateTime toReserved = LocalDateTime.parse("2023-12-10T10:00");

        LocalDateTime fromReserved2 = LocalDateTime.parse("2023-12-05T15:00");
        LocalDateTime toReserved2 = LocalDateTime.parse("2023-12-20T10:00");

        Reservation reservation = new Reservation(1, reservedRoom, guest, fromReserved, toReserved);
        Reservation reservation2 = new Reservation(2, room, guest, fromReserved2, toReserved2);

        ReservationService reservationService = mock(ReservationService.class);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        reservations.add(reservation2);
        when(reservationService.getAllReservations()).thenReturn(reservations);

        RoomService roomService = new RoomService();
        roomService.setRepository(roomRepository);
        roomService.setReservationService(reservationService);

        LocalDate from = LocalDate.parse("2023-12-04");
        LocalDate to = LocalDate.parse("2023-12-06");

        //when
        List<Room> availableRooms = roomService.getAvailableRooms(from, to);

        //then
        assertEquals(1, availableRooms.size());
        assertEquals(303, availableRooms.get(0).getNumber());
    }
}
