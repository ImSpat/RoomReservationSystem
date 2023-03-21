package org.example.domain.room;

public class Room {

    private final int number;
    private final BedType[] beds;

    Room(int number, BedType[] bedTypes) {
        this.number = number;
        this.beds = bedTypes;
    }

    public String getInfo() {

        StringBuilder bedInfo = new StringBuilder("Rodzaje łóżek w pokoju:\n");
        for (BedType bed : beds) {
            bedInfo.append("\t").append(bed).append("\n");
        }

        return String.format("Numer %d %s", this.number, bedInfo);
    }

    public String toCSV() {
        StringBuilder bedInfo = new StringBuilder();
        for (BedType bed : beds) {
            bedInfo.append(bed).append(",");
        }
        bedInfo.deleteCharAt(bedInfo.length()-1);

        return String.format("%d,%s%s", this.number, bedInfo, System.getProperty("line.separator"));
    }
}