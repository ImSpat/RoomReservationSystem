package org.example;

import org.example.exceptions.PersistenceToFileException;
import org.example.ui.text.TextUI;
import org.example.util.Properties;

import java.io.IOException;

public class App {

    private static final TextUI textUI = new TextUI();

    public static void main(String[] args) {

        try {
            Properties.createDataDirectory();
        } catch (IOException e) {
            throw new PersistenceToFileException(Properties.DATA_DIRECTORY.toString(), "create", "directory");
        }
        textUI.showSystemInfo();
        textUI.showMainMenu();


    }


}