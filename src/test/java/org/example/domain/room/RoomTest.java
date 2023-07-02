package org.example.domain.room;

import org.example.domain.room.dto.RoomDTO;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RoomTest {

    @Test
    public void toCSVTest() {

        //given
        List<BedType> beds = Arrays.asList(BedType.values());
        Room room = new Room(1, 302, beds);

        //when
        String csvCreated = room.toCSV();

        //then
        String csvTemplate = "1,302,Pojedyncze,Podwójne,Królewskie"+System.getProperty("line.separator");
        assertEquals(csvTemplate, csvCreated, "Porównanie formatów CSV");
    }

    @Test
    public void toCSVWithNullBedListTest(){

        //given
        Room room = new Room(1, 302, null);

        //when
        String csvCreated = room.toCSV();

        //then
        assertNotNull(room.getBeds());

        String csvTemplate = "1,302,"+System.getProperty("line.separator");
        assertEquals(csvTemplate, csvCreated, "Porównanie formatów CSV przy liscie lozek == null");
    }

    @Test
    public void toDTOTest(){

        //given
        List<BedType> beds = Arrays.asList((BedType.values()));
        Room room = new Room(1, 302, beds);

        //when
        RoomDTO roomDTO = room.generateDTO();

        //then
        assertEquals(roomDTO.getId(),1);
        assertEquals(roomDTO.getNumber(), 302);
        assertEquals(roomDTO.getBedsCount(), 3);
        assertEquals(roomDTO.getRoomSize(), 5);
        assertEquals(roomDTO.getBeds(), "Pojedyncze,Podwójne,Królewskie");
    }

    @Test
    public void toDTOFromRoomWithNullBedsListTest() {

        //given
        Room room = new Room(1, 302, null);

        //when
        RoomDTO roomDTO = room.generateDTO();

        //then
        assertEquals(roomDTO.getId(),1);
        assertEquals(roomDTO.getNumber(), 302);
        assertEquals(roomDTO.getBedsCount(), 0);
        assertEquals(roomDTO.getRoomSize(), 0);
        assertEquals(roomDTO.getBeds(), "");
    }


}
