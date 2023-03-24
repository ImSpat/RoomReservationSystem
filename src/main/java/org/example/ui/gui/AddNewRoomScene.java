package org.example.ui.gui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.domain.ObjectPool;
import org.example.domain.room.RoomService;
import org.example.domain.room.dto.RoomDTO;

import java.util.ArrayList;
import java.util.List;

public class AddNewRoomScene {

    private final Scene mainScene;
    private final List<ComboBox<String>> comboBoxes = new ArrayList<>();
    private final RoomService roomService = ObjectPool.getRoomService();

    public AddNewRoomScene(Stage stg, TableView<RoomDTO> tableView) {

        Label roomNumberLabel = new Label("Numer pokoju:");
        TextField roomNumberField = new TextField();
        HBox roomNumber = new HBox(roomNumberLabel, roomNumberField);

        Label bedTypeLabel = new Label("Typy łóżek:");
        Button addNewBedButton = new Button("Dodaj kolejne łóżko");

        HBox bedTypeRow = new HBox(bedTypeLabel, addNewBedButton);

        VBox bedsVerticalLayout = new VBox(bedTypeRow, getComboBox());

        addNewBedButton.setOnAction(actionEvent -> {
            bedsVerticalLayout.getChildren().add(getComboBox());
        });

        Button addNewRoomButton = new Button("Dodaj nowy pokój");
        addNewRoomButton.setOnAction(actionEvent -> {
            int newRoomNumber = Integer.parseInt(roomNumberField.getText());
            List<String> bedTypes = new ArrayList<>();
            this.comboBoxes.forEach(comboBoxes -> bedTypes.add(comboBoxes.getValue()));
            this.roomService.createNewRoom(newRoomNumber, bedTypes);
            tableView.getItems().clear();
            List<RoomDTO> allAsDTO = roomService.getAllAsDTO();
            tableView.getItems().addAll(allAsDTO);
            stg.close();
        });

        VBox mainFormLayout = new VBox(roomNumber, bedsVerticalLayout, addNewRoomButton);

        this.mainScene = new Scene(mainFormLayout, 640, 480);
    }

    private ComboBox<String> getComboBox() {
        ComboBox<String> bedTypeField = new ComboBox<>();
        bedTypeField.getItems().addAll("Pojedyncze", "Podwójne", "Królewskie");
        bedTypeField.setValue("Pojedyncze");
        this.comboBoxes.add(bedTypeField);
        return bedTypeField;
    }

    public Scene getMainScene() {
        return mainScene;
    }
}