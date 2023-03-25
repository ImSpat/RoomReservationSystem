package org.example.ui.gui;

import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import org.example.ui.gui.guests.GuestsTab;
import org.example.ui.gui.reservations.ReservationsTab;
import org.example.ui.gui.rooms.RoomsTab;

public class MainTabView {
    private TabPane mainTabs;

    public MainTabView(Stage primaryStage) {
        this.mainTabs = new TabPane();

        RoomsTab roomsTab = new RoomsTab(primaryStage);
        ReservationsTab reservationsTab = new ReservationsTab(primaryStage);
        GuestsTab guestsTab = new GuestsTab(primaryStage);

        this.mainTabs.getTabs().addAll(reservationsTab.getReservationTab(), guestsTab.getGuestTab(), roomsTab.getRoomTab());
    }

    TabPane getMainTabs() {
        return mainTabs;
    }
}