package org.example.ui.gui.reservations;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.domain.ObjectPool;
import org.example.domain.guest.GuestService;
import org.example.domain.guest.dto.GuestDTO;
import org.example.domain.reservation.ReservationService;
import org.example.domain.reservation.dto.ReservationDTO;
import org.example.domain.room.RoomService;
import org.example.domain.room.dto.RoomDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddNewReservationScene {

    private Scene mainScene;
    private RoomService roomService = ObjectPool.getRoomService();
    private GuestService guestService = ObjectPool.getGuestService();
    private ReservationService reservationService = ObjectPool.getReservationService();

    public AddNewReservationScene(Stage modalStage, TableView<ReservationDTO> tableView) {

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(15);

        Label fromDateLabel = new Label("Data rozpoczęcia rezerwacji:");
        DatePicker fromDateField = new DatePicker();

        gridPane.add(fromDateLabel, 0, 0);
        gridPane.add(fromDateField, 1, 0);

        Label toDateLabel = new Label("Data zakończenia rezerwacji:");
        DatePicker toDateField = new DatePicker();

        gridPane.add(toDateLabel, 0, 1);
        gridPane.add(toDateField, 1, 1);

        List<RoomDTO> allAsDTO = this.roomService.getAllAsDTO();
        List<RoomSelectionItem> roomSelectionItems = new ArrayList<>();
        allAsDTO.forEach(dto -> {
            roomSelectionItems.add(new RoomSelectionItem(dto.getNumber(), (int) dto.getId()));
        });

        List<GuestDTO> guestsAsDTO = this.guestService.getGuestsAsDTO();
        List<GuestSelectionItem> guestSelectionItems = new ArrayList<>();
        guestsAsDTO.forEach(dto -> {
            guestSelectionItems.add(new GuestSelectionItem(dto.getFirstName(), dto.getLastName(), (int) dto.getId()));
        });

        Label roomLabel = new Label("Pokój:");
        ComboBox<RoomSelectionItem> roomField = new ComboBox<>();
        roomField.getItems().addAll(roomSelectionItems);

        fromDateField.valueProperty().addListener((ov, oldValue, newValue) -> {
            LocalDate from = newValue;
            LocalDate to = toDateField.getValue();
            if(from!=null && to!=null) {
                List<RoomDTO> availableRoomsAsDTO = this.roomService.getAvailableRoomsAsDTO(from, to);
                roomSelectionItems.clear();
                for(RoomDTO dto : availableRoomsAsDTO) {
                    roomSelectionItems.add(
                            new RoomSelectionItem(dto.getNumber(), (int)dto.getId()));
                }
                roomField.getItems().clear();
                roomField.getItems().addAll(roomSelectionItems);
            }
        });

        toDateField.valueProperty().addListener((ov, oldValue, newValue) -> {
            LocalDate from = fromDateField.getValue();
            LocalDate to = newValue;
            if(from!=null && to!=null) {
                List<RoomDTO> availableRoomsAsDTO = this.roomService.getAvailableRoomsAsDTO(from, to);
                roomSelectionItems.clear();
                for(RoomDTO dto : availableRoomsAsDTO) {
                    roomSelectionItems.add(
                            new RoomSelectionItem(dto.getNumber(), (int)dto.getId()));
                }
                roomField.getItems().clear();
                roomField.getItems().addAll(roomSelectionItems);
            }
        });

        gridPane.add(roomLabel, 0, 2);
        gridPane.add(roomField, 1, 2);

        Label guestLabel = new Label("Rezerwujący:");
        ComboBox<GuestSelectionItem> guestField = new ComboBox<>();
        guestField.getItems().addAll(guestSelectionItems);

        gridPane.add(guestLabel, 0, 3);
        gridPane.add(guestField, 1, 3);

        Button btn = new Button("Utwórz rezerwację");

        btn.setOnAction(actionEvent -> {
            LocalDate from = fromDateField.getValue();
            LocalDate to = toDateField.getValue();
            int guestId = guestField.getValue().getId();
            long roomId = roomField.getValue().getId();

            try {
                this.reservationService.createNewReservation(from, to, roomId, guestId);

                tableView.getItems().clear();
                tableView.getItems().addAll(this.reservationService.getReservationsAsDTO());
                modalStage.close();
            } catch (IllegalArgumentException ex) {
                Label error = new Label("Niepoprawne daty rezerwacji");
                error.setTextFill(Color.RED);
                gridPane.add(error, 0, 5);
            }
        });

        gridPane.add(btn, 1, 4);

        this.mainScene = new Scene(gridPane, 640, 480);
        this.mainScene.getStylesheets().add(getClass().getClassLoader()
                .getResource("hotelReservation.css").toExternalForm());
    }

    public Scene getMainScene() {
        return this.mainScene;
    }
}
