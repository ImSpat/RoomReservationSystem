package org.example.domain.guest;


import org.example.domain.ObjectPool;
import org.example.domain.guest.dto.GuestDTO;

import java.util.ArrayList;
import java.util.List;

public class GuestService {

    private final GuestRepository repository = ObjectPool.getGuestRepository();

    private static final GuestService instance = new GuestService();

    private GuestService() {
    }

    public static GuestService getInstance() {
        return instance;
    }

    public Guest createNewGuest(String firstName, String lastName, int age, boolean isMale) {
        Gender gender = Gender.FEMALE;
        if (isMale) {
            gender = Gender.MALE;
        }
        return repository.createNewGuest(firstName, lastName, age, gender);
    }

    public List<Guest> getAllGuests() {
        return repository.getAll();
    }

    public void saveAll() {
        repository.saveAll();
    }

    public void readAll() {
        repository.readAll();
    }

    public void removeGuest(long id) {
        repository.remove(id);
    }

    public void editGuest(long id, String firstName, String lastName, int age, boolean isMale) {
        Gender gender = Gender.FEMALE;
        if (isMale) {
            gender = Gender.MALE;
        }
        repository.edit(id, firstName, lastName, age, gender);
    }

    public Guest getGuestById(long Id) {
        return repository.findById(Id);
    }

    public List<GuestDTO> getGuestsAsDTO() {
        List<GuestDTO> result = new ArrayList<>();

        List<Guest> guests = repository.getAll();

        if (guests!=null){
            for (Guest guest : guests) {
                GuestDTO dto = guest.getAsDTO();
                result.add(dto);
            }
        }
        return result;
    }
}
