package com.company;

import com.company.gui.MainFrame;
import com.company.orderoperations.OrderDatabaseManager;

public class Main {
    private static final String fullFilePath = "C:/pempera/database/OrderDatabase.sqlite";


    public static void main(String[] args) {
	// write your code here
        OrderDatabaseManager.setDatabaseLocation(fullFilePath);
        OrderDatabaseManager.selectAll();
        MainFrame mainWindow = new MainFrame();
        mainWindow.setSize(370,75);
        mainWindow.setLocation(400,400);
        mainWindow.setVisible(true);
    }
}
