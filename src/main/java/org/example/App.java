package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.domain.guest.GuestService;
import org.example.domain.reservation.ReservationService;
import org.example.domain.room.RoomService;
import org.example.exceptions.PersistenceToFileException;
import org.example.ui.gui.PrimaryStage;
import org.example.util.Properties;

import java.io.IOException;

public class App extends Application {

    //    private static final TextUI textUI = new TextUI();
    private static final GuestService guestService = new GuestService();
    private static final RoomService roomService = new RoomService();
    private static final ReservationService reservationService = new ReservationService();

    public static void main(String[] args) {

        try {
            Properties.createDataDirectory();
            System.out.println("Trwa ładowanie danych");
            guestService.readAll();
            roomService.readAll();
            reservationService.readAll();
        } catch (IOException e) {
            throw new PersistenceToFileException(Properties.DATA_DIRECTORY.toString(), "create", "directory");
        }
        Application.launch(args);
//        textUI.showSystemInfo();
//        textUI.showMainMenu();
    }

    public void start(Stage primaryStage) {
        PrimaryStage primary = new PrimaryStage();
        primary.initialize(primaryStage);
    }


}