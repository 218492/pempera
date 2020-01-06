package com.company;

import com.company.gui.MainFrame;
import com.company.globaloperations.DatabaseManager;

public class Main {
    private static final String fullFilePath = "C:/pempera/database/OrderDatabase.sqlite";


    public static void main(String[] args) {
	// write your code here
        DatabaseManager.setDatabaseLocation(fullFilePath);
        DatabaseManager.selectAll();
        MainFrame mainWindow = new MainFrame();
        mainWindow.setSize(370,75);
        mainWindow.setLocation(400,400);
        mainWindow.setVisible(true);
    }
}
