package org.example.domain.room;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class RoomJpaRepository implements RoomRepository{

    private final static RoomRepository instance = new RoomJpaRepository();

    public static RoomRepository getInstance() {
        return instance;
    }

    private static EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("thePersistenceUnit");
    private static EntityManager em = factory.createEntityManager();

    @Override
    public void saveAll() {
        System.out.println("not implemented yet");

    }

    @Override
    public void readAll() {
        System.out.println("not implemented yet");

    }

    @Override
    public void remove(long id) {
        EntityTransaction tx = em.getTransaction();
        Room roomToRemove = em.find(Room.class, id);
        tx.begin();
        em.remove(roomToRemove);
        tx.commit();
    }

    @Override
    public void edit(long id, int number, List<BedType> bedTypes) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Room room = em.find(Room.class, id);
        room.setNumber(number);
        room.setBeds(bedTypes);
        tx.commit();
        System.out.println("Obiekt room po edycji to: " + room.getInfo());
    }

    @Override
    public Room getById(long id) {
        return em.find(Room.class, id);
    }

    @Override
    public Room createNewRoom(int number, List<BedType> bedTypes) {

        Room newRoom = new Room(number, bedTypes);

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(newRoom);
        tx.commit();

        return newRoom;
    }

    @Override
    public List<Room> getAll() {
        return em.createQuery("SELECT a FROM Room a", Room.class).getResultList();
    }
}
