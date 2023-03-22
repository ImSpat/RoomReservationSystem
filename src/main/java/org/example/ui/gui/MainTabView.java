package org.example.ui.gui;

import javafx.scene.control.TabPane;

public class MainTabView {
    private TabPane mainTabs;

    public MainTabView() {
        this.mainTabs = new TabPane();

        RoomsTab roomsTab = new RoomsTab();
        ReservationsTab reservationsTab = new ReservationsTab();
        GuestsTab guestsTab = new GuestsTab();

        this.mainTabs.getTabs().addAll(reservationsTab.getReservationTab(), guestsTab.getGuestTab(), roomsTab.getRoomTab());
    }

    TabPane getMainTabs() {
        return mainTabs;
    }
}