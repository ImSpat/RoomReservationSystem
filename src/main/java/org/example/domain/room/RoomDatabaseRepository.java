package org.example.domain.room;

import java.util.List;

public class RoomDatabaseRepository implements RoomRepository {

    private final static RoomRepository instance = new RoomDatabaseRepository();

    private RoomDatabaseRepository() {
    }

    public static RoomRepository getInstance() {
        return instance;
    }

    @Override
    public void saveAll() {

    }

    @Override
    public void readAll() {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void edit(int id, int number, List<BedType> bedTypes) {

    }

    @Override
    public Room getById(int id) {
        return null;
    }

    @Override
    public Room createNewRoom(int number, List<BedType> bedTypes) {
        return null;
    }

    @Override
    public List<Room> getAll() {
        return null;
    }
}