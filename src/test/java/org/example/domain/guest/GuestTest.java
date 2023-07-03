package org.example.domain.guest;

import org.example.domain.guest.Gender;
import org.example.domain.guest.Guest;
import org.example.domain.guest.dto.GuestDTO;
import org.example.domain.room.BedType;
import org.example.domain.room.Room;
import org.example.domain.room.dto.RoomDTO;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GuestTest {

    @Test
    public void toCSVTest(){

        //given
        Guest guest = new Guest(1, "Jan", "Kowalski", 30, Gender.MALE);

        //when
        String csvCreated = guest.toCSV();

        //then
        String csvTemplate = "1,Jan,Kowalski,30,Mężczyzna"+System.getProperty("line.separator");
        assertEquals(csvTemplate, csvCreated, "Porównanie CSV");
    }

    @Test
    public void toCSVWithNullGenderTest(){

        //given
        Guest guestMale = new Guest(1, "Jan", "Kowalski", 30, null);
        Guest guestFemale = new Guest(1, "Janina", "Kowalska", 30, null);

        //when
        String csvCreatedMale = guestMale.toCSV();
        String csvCreatedFemale = guestFemale.toCSV();

        //then
        assertNotNull(guestMale.getGender());
        assertNotNull(guestFemale.getGender());

        String csvTemplateMale = "1,Jan,Kowalski,30,Mężczyzna"+System.getProperty("line.separator");
        String csvTemplateFemale = "1,Janina,Kowalska,30,Kobieta"+System.getProperty("line.separator");
        assertEquals(csvTemplateMale, csvCreatedMale, "Porównanie formatów CSV przy gender == null");
        assertEquals(csvTemplateFemale, csvCreatedFemale, "Porównanie formatów CSV przy gender == null");
    }

    @Test
    public void toDTOTest(){

        //given
        Guest guest = new Guest(1, "Jan", "Kowalski", 30, Gender.MALE);

        //when
        GuestDTO guestDTO = guest.getAsDTO();

        //then
        assertEquals(guestDTO.getId(),1);
        assertEquals(guestDTO.getFirstName(), "Jan");
        assertEquals(guestDTO.getLastName(), "Kowalski");
        assertEquals(guestDTO.getAge(), 30);
        assertEquals(guestDTO.getGender(), "Mężczyzna");
    }

    @Test
    public void toDTOFromGuestWithNullGenderTest() {

        //given
        Guest guest = new Guest(1, "Jan", "Kowalski", 30, null);

        //when
        GuestDTO guestDTO = guest.getAsDTO();

        //then
        assertEquals(guestDTO.getId(),1);
        assertEquals(guestDTO.getFirstName(), "Jan");
        assertEquals(guestDTO.getLastName(), "Kowalski");
        assertEquals(guestDTO.getAge(), 30);
        assertEquals(guestDTO.getGender(), "Mężczyzna");
    }
}
