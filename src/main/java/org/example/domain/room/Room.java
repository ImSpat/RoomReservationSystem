package org.example.domain.room;

import org.example.domain.room.dto.RoomDTO;

public class Room {

    private final int id;
    private final int number;
    private final BedType[] beds;

    Room(int id, int number, BedType[] bedTypes) {
        this.id = id;
        this.number = number;
        this.beds = bedTypes;
    }

    public int getId() {
        return id;
    }

    public String getInfo() {

        StringBuilder bedInfo = new StringBuilder("Rodzaje łóżek w pokoju:\n");
        for (BedType bed : beds) {
            bedInfo.append("\t").append(bed).append("\n");
        }

        return String.format("ID: %d numer: %d %s", this.id, this.number, bedInfo);
    }

    public String toCSV() {
        StringBuilder bedInfo = new StringBuilder();
        for (BedType bed : beds) {
            bedInfo.append(bed).append(",");
        }
        bedInfo.deleteCharAt(bedInfo.length() - 1);

        return String.format("%d,%d,%s%s", this.id, this.number, bedInfo, System.getProperty("line.separator"));
    }

    public RoomDTO generateDTO() {

        String[] bedsAsString = new String[this.beds.length];

        for (int i = 0; i < this.beds.length; i++) {
            bedsAsString[i] = this.beds[i].toString();
        }

        String bedTypes = String.join(",", bedsAsString);

        return new RoomDTO(this.id, this.number, bedTypes);
    }
}