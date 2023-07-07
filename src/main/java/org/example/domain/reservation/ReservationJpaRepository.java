package org.example.domain.reservation;

import org.example.domain.guest.Guest;
import org.example.domain.room.Room;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationJpaRepository implements ReservationRepository{

    private final static ReservationRepository instance = new ReservationJpaRepository();

    private static EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("thePersistenceUnit");
    private static EntityManager em = factory.createEntityManager();

    public static ReservationRepository getInstance() {
        return  instance;
    }

    @Override
    public Reservation createNewReservation(Room room, Guest guest, LocalDateTime from, LocalDateTime to) {

        Reservation newReservation = new Reservation(room, guest, from, to);

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(newReservation);
        tx.commit();

        return newReservation;
    }

    @Override
    public List<Reservation> getAll() {
        return em.createQuery("SELECT a FROM Reservation a", Reservation.class).getResultList();
    }

    @Override
    public void readAll() {
        System.out.println("not implemented yet");
    }

    @Override
    public void saveAll() {
        System.out.println("not implemented yet");
    }

    @Override
    public void remove(long id) {

        EntityTransaction tx = em.getTransaction();

        Reservation reservationToRemove = em.find(Reservation.class, id);
        tx.begin();
        em.remove(reservationToRemove);
        tx.commit();

    }
}
