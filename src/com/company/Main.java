package com.company;

import com.company.globaloperations.DatabaseManager;
import com.company.gui.MainFrame;

public class Main {
    private static final String fullFilePath = "C:/pempera/database/OrderDatabase.sqlite";

    public static void main(String[] args) {
	// write your code here
        DatabaseManager.setDatabaseLocation(fullFilePath);

        MainFrame mainWindow = new MainFrame();
        mainWindow.setSize(370,75);
        mainWindow.setLocation(400,400);
        mainWindow.setVisible(true);
    }
}
