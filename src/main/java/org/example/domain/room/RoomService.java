package org.example.domain.room;

import org.example.domain.ObjectPool;
import org.example.domain.reservation.Reservation;
import org.example.domain.reservation.ReservationService;
import org.example.domain.room.dto.RoomDTO;
import org.example.exceptions.WrongOptionException;
import org.example.util.SystemUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomService {

    private RoomRepository repository = ObjectPool.getRoomRepository();
    private ReservationService reservationService = ObjectPool.getReservationService();
//    private final static RoomService instance = new RoomService();

    //    public static RoomService getInstance() {
//        return instance;
//    }

    public RoomService() {
    }

    public Room createNewRoom(int number, List<String> bedTypesAsString) {

        List<BedType> bedTypes = getBedTypes(bedTypesAsString);

        return repository.createNewRoom(number, bedTypes);
    }

    public Room createNewRoom(int number, int[] bedTypesOptions) {

        List<BedType> bedTypes = getBedTypes(bedTypesOptions);

        return repository.createNewRoom(number, bedTypes);
    }

    public List<Room> getAllRooms() {
        return repository.getAll();
    }

    public void saveAll() {
        repository.saveAll();
    }

    public void readAll() {
        repository.readAll();
    }

    public void removeRoom(long id) {
        repository.remove(id);
    }

    public void editRoom(int id, int number, int[] bedTypesOptions) {
        List<BedType> bedTypes = getBedTypes(bedTypesOptions);
        repository.edit(id, number, bedTypes);
    }

    public void editRoom(long id, int number, List<String> bedTypesAsString) {
        List<BedType> bedTypes = getBedTypes(bedTypesAsString);
        this.repository.edit(id, number, bedTypes);
    }

    public Room getRoomById(long roomId) {
        return repository.getById(roomId);
    }

    public List<RoomDTO> getAllAsDTO() {

        List<RoomDTO> result = new ArrayList<>();

        List<Room> allRooms = repository.getAll();

        for (Room room : allRooms) {
            RoomDTO dto = room.generateDTO();
            result.add(dto);
        }

        return result;
    }

    List<BedType> getBedTypes(int[] bedTypesOptions) {
        BedType[] bedTypes = new BedType[bedTypesOptions.length];
        for (int i = 0; i < bedTypesOptions.length; i++) {
            BedType bedType;
            if (bedTypesOptions[i] == 1) {
                bedType = BedType.SINGLE;
            } else if (bedTypesOptions[i] == 2) {
                bedType = BedType.DOUBLE;
            } else if (bedTypesOptions[i] == 3) {
                bedType = BedType.KING_SIZE;
            } else {
                throw new WrongOptionException("Wrong option when selecting bed type");
            }
            bedTypes[i] = bedType;
        }
        return Arrays.asList(bedTypes);
    }

    private List<BedType> getBedTypes(List<String> bedTypesAsString) {
        BedType[] bedTypes = new BedType[bedTypesAsString.size()];
        for (int i = 0; i < bedTypesAsString.size(); i++) {
            BedType bedType;
            if (bedTypesAsString.get(i).equals(SystemUtils.SINGLE_BED)) {
                bedType = BedType.SINGLE;
            } else if (bedTypesAsString.get(i).equals(SystemUtils.DOUBLE_BED)) {
                bedType = BedType.DOUBLE;
            } else if (bedTypesAsString.get(i).equals(SystemUtils.KING_SIZE)) {
                bedType = BedType.KING_SIZE;
            } else {
                throw new WrongOptionException("Wrong option when selecting bed type");
            }
            bedTypes[i] = bedType;
        }
        return Arrays.asList(bedTypes);
    }

    public List<Room> getAvailableRooms(LocalDate from, LocalDate to) {

        if (from==null || to==null){
            throw new IllegalArgumentException("Parameters cant be null");
        }

        if (to.isBefore(from)){
            throw new IllegalArgumentException("End date cant be before start date");
        }

        List<Room> availableRooms = this.repository.getAll();

        LocalDateTime fromWithHour = from.atTime(SystemUtils.HOTEL_NIGHT_START_HOUR, SystemUtils.HOTEL_NIGHT_START_MINUTE);
        LocalDateTime toWithHour = to.atTime(SystemUtils.HOTEL_NIGHT_END_HOUR, SystemUtils.HOTEL_NIGHT_END_MINUTE);

        if(this.reservationService==null){
            this.reservationService = ObjectPool.getReservationService();
        }
        List<Reservation> reservations = this.reservationService.getAllReservations();

        for (Reservation reservation : reservations){
            if (reservation.getFrom().isEqual(fromWithHour)){
                availableRooms.remove(reservation.getRoom());
            } else if (reservation.getTo().isEqual(toWithHour)) {
                availableRooms.remove(reservation.getRoom());
            } else if (reservation.getFrom().isAfter(fromWithHour) &&
                        reservation.getFrom().isBefore(toWithHour)) {
                availableRooms.remove(reservation.getRoom());
            } else if (reservation.getTo().isAfter(fromWithHour) &&
                    reservation.getTo().isBefore(toWithHour)) {
                availableRooms.remove(reservation.getRoom());
            } else if (fromWithHour.isAfter(reservation.getFrom()) &&
                    toWithHour.isBefore(reservation.getTo())) {
                availableRooms.remove(reservation.getRoom());
            }
        }

        return availableRooms;
    }

    public List<RoomDTO> getAvailableRoomsAsDTO(LocalDate from, LocalDate to){
        List<Room> availableRooms = this.getAvailableRooms(from, to);
        List<RoomDTO> result = new ArrayList<>();

        for (Room room : availableRooms) {
            result.add(room.generateDTO());
        }

        return result;
    }

    public void setRepository(RoomRepository roomRepository) {
        this.repository = roomRepository;
    }

    public void setReservationService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
}
