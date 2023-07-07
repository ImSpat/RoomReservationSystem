package org.example.domain.guest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;


public class GuestJpaRepository implements GuestRepository {


    private final static GuestRepository instance = new GuestJpaRepository();

    public static GuestRepository getInstance() {
        return instance;
    }

    private static EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("thePersistenceUnit");
    private static EntityManager em = factory.createEntityManager();

    @Override
    public Guest createNewGuest(String firstName, String lastName, int age, Gender gender) {

        Guest newGuest = new Guest(firstName, lastName, age, gender);

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(newGuest);
        transaction.commit();

        return newGuest;
    }

    @Override
    public List<Guest> getAll() {
        return em.createQuery("SELECT a FROM Guest a", Guest.class).getResultList();
    }

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
        Guest guestToRemove = em.find(Guest.class, id);
        tx.begin();
        em.remove(guestToRemove);
        tx.commit();
    }

    @Override
    public void edit(long id, String firstName, String lastName, int age, Gender gender) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Guest guest = em.find(Guest.class, id);
        guest.setFirstName(firstName);
        guest.setLastName(lastName);
        guest.setAge(age);
        guest.setGender(gender);
        tx.commit();
        System.out.println("Obiekt gość po edycji to: " + guest.getInfo());
    }

    @Override
    public Guest findById(long id) {
        return em.find(Guest.class, id);
    }
}