package org.example;

import org.example.ui.text.TextUI;

public class App {

    private static final TextUI textUI = new TextUI();

    public static void main(String[] args) {

        String hotelName = "Overlook";
        int systemVersion = 1;
        boolean isDeveloperVersion = true;

        textUI.showSystemInfo(hotelName, systemVersion, isDeveloperVersion);
        textUI.showMainMenu();


    }


}