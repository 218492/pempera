package com.company;

import com.company.gui.MainFrame;

public class Main {

    public static void main(String[] args) {
	// write your code here
        MainFrame mainWindow = new MainFrame();
        mainWindow.setSize(370,75);
        mainWindow.setLocation(400,400);
        mainWindow.setVisible(true);
    }
}
