package org.example.domain.guest;


import org.example.domain.guest.dto.GuestDTO;

import java.util.ArrayList;
import java.util.List;

public class GuestService {

    private final static GuestRepository repository = new GuestRepository();

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

    public void removeGuest(int id) {
        repository.remove(id);
    }

    public void editGuest(int id, String firstName, String lastName, int age, boolean isMale) {
        Gender gender = Gender.FEMALE;
        if (isMale) {
            gender = Gender.MALE;
        }
        repository.edit(id, firstName, lastName, age, gender);
    }

    public Guest getGuestById(int Id) {
        return repository.findById(Id);
    }

    public List<GuestDTO> getGuestsAsDTO() {
        List<GuestDTO> result = new ArrayList<>();
        List<Guest> guests = repository.getAll();

        for (Guest guest : guests) {
            GuestDTO dto = guest.getAsDTO();
            result.add(dto);
        }
        return result;
    }
}
