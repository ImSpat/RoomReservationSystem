package org.example.domain.room;

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
        Room newRoom = new Room(number, bedTypes);
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
            e.printStackTrace();
        }
    }

    void readAll() {

        String name = "rooms.csv";
        Path file = Paths.get(Properties.DATA_DIRECTORY.toString(), name);

        try {
            String data = Files.readString(file, StandardCharsets.UTF_8);
            String[] roomsAsString = data.split(System.getProperty("line.separator"));

            for (String roomAsString : roomsAsString) {
                String[] roomData = roomAsString.split(",");
                int number = Integer.parseInt(roomData[0]);
                BedType[] bedTypes = new BedType[roomData.length - 1];
                for (int i = 1; i < roomData.length; i++) {
                    int j = i - 1;
                    bedTypes[j] = BedType.valueOf(roomData[i]);
                }
                createNewRoom(number, bedTypes);
            }
        } catch (IOException e) {
            System.out.println("Nie udało się odczytać pliku z danymi");
            e.printStackTrace();
        }


    }
}
