package org.example.domain.room;

import org.example.exceptions.PersistenceToFileException;
import org.example.util.Properties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository {

    private final List<Room> rooms = new ArrayList<>();

    Room createNewRoom(int number, BedType[] bedTypes) {
        Room newRoom = new Room(findNewId(), number, bedTypes);
        rooms.add(newRoom);
        return newRoom;
    }

    Room addNewRoomFromFile(int id, int number, BedType[] bedTypes) {
        Room newRoom = new Room(id, number, bedTypes);
        rooms.add(newRoom);
        return newRoom;
    }

    public List<Room> getAll() {
        return rooms;
    }

    void saveAll() {

        StringBuilder sb = new StringBuilder();
        for (Room room : rooms) {
            sb.append(room.toCSV());
        }

        String name = "rooms.csv";
        Path file = Paths.get(Properties.DATA_DIRECTORY.toString(), name);
        try {
            Files.writeString(file, sb.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new PersistenceToFileException(file.toString(), "write", "rooms data");
        }
    }

    void readAll() {

        String name = "rooms.csv";
        Path file = Paths.get(Properties.DATA_DIRECTORY.toString(), name);

        if (!Files.exists(file)){
            return;
        }

        try {
            String data = Files.readString(file, StandardCharsets.UTF_8);
            String[] roomsAsString = data.split(System.getProperty("line.separator"));

            for (String roomAsString : roomsAsString) {
                String[] roomData = roomAsString.split(",");
                int id = Integer.parseInt(roomData[0]);
                int number = Integer.parseInt(roomData[1]);
                BedType[] bedTypes = new BedType[roomData.length - 2];
                for (int i = 2; i < roomData.length; i++) {
                    int j = i - 2;
                    bedTypes[j] = BedType.valueOf(roomData[i]);
                }
                addNewRoomFromFile(id, number, bedTypes);
            }
        } catch (IOException e) {
            throw new PersistenceToFileException(file.toString(), "read", "rooms data");

        }
    }

    private int findNewId() {
        int max = 0;
        for (Room room : rooms) {
            if (room.getId() > max) {
                max = room.getId();
            }
        }
        return max + 1;
    }
}
