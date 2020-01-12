package com.company;

import com.company.globaloperations.DatabaseManager;
import com.company.gui.MainFrame;

public class Main {
    private static final String fullFilePath = "C:/pempera/database/OrderDatabase.sqlite";

    public static void main(String[] args) {
	// write your code here
        DatabaseManager.setDatabaseLocation(fullFilePath);
        MainFrame.main(args);
    }
}
