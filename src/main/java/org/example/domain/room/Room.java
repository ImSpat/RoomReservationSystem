package org.example.domain.room;

import org.example.domain.room.dto.RoomDTO;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private final long id;
    private int number;
    private List<BedType> beds;

    Room(long id, int number, List<BedType> bedTypes) {
        this.id = id;
        this.number = number;
        this.beds = bedTypes;
    }

    public long getId() {
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

        List<String> bedsAsString = new ArrayList<>();

        for (int i = 0; i < this.beds.size(); i++) {
            bedsAsString.add(this.beds.get(i).toString());
        }

        String bedTypes = String.join(",", bedsAsString);

        int roomSize = 0;
        for (BedType bedType : beds) {
            roomSize += bedType.getSize();
        }

        return new RoomDTO(this.id, this.number, bedTypes, beds.size(), roomSize);
    }

    public int getNumber() {
        return this.number;
    }

    public void addBed(BedType bedType) {
        this.beds.add(bedType);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setBeds(List<BedType> beds) {
        this.beds = beds;
    }
}