package org.example.ui.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.domain.room.RoomService;
import org.example.domain.room.dto.RoomDTO;

import java.util.List;

public class RoomsTab {

    private Tab roomTab;
    private RoomService roomService = new RoomService();

    public RoomsTab() {

        TableView<RoomDTO> tableView = new TableView<>();

        TableColumn<RoomDTO, Integer> numberColumn = new TableColumn<>("Numer");
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn<RoomDTO, String> bedsColumn = new TableColumn<>("Typy łóżek");
        bedsColumn.setCellValueFactory(new PropertyValueFactory<>("beds"));

        tableView.getColumns().addAll(numberColumn, bedsColumn);

        List<RoomDTO> allAsDTO = roomService.getAllAsDTO();

        tableView.getItems().addAll(allAsDTO);

        this.roomTab = new Tab("Pokoje", tableView);
        this.roomTab.setClosable(false);
    }

    Tab getRoomTab() {
        return roomTab;
    }
}