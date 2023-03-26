package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.domain.ObjectPool;
import org.example.domain.guest.GuestService;
import org.example.domain.reservation.ReservationService;
import org.example.domain.room.RoomService;
import org.example.exceptions.PersistenceToFileException;
import org.example.ui.gui.PrimaryStage;
import org.example.util.SystemUtils;

import java.io.IOException;

public class App extends Application {

    //    private static final TextUI textUI = new TextUI();
    private static final GuestService guestService = ObjectPool.getGuestService();
    private static final RoomService roomService = ObjectPool.getRoomService();
    private static final ReservationService reservationService = ObjectPool.getReservationService();

    public static void main(String[] args) {

        try {
            SystemUtils su = new SystemUtils();
            SystemUtils.createDataDirectory();
            su.createDatabaseConnection();
            System.out.println("Trwa ładowanie danych");
            guestService.readAll();
            roomService.readAll();
            reservationService.readAll();
        } catch (IOException e) {
            throw new PersistenceToFileException(SystemUtils.DATA_DIRECTORY.toString(), "create", "directory");
        }
        Application.launch(args);
//        textUI.showSystemInfo();
//        textUI.showMainMenu();
    }

    @Override
    public void start(Stage primaryStage) {
        PrimaryStage primary = new PrimaryStage();
        primary.initialize(primaryStage);
    }

    @Override
    public void stop() {
        System.out.println("Wychodzę z aplikacji. Zapisuję dane.");
        guestService.saveAll();
        roomService.saveAll();
        reservationService.saveAll();
    }


}