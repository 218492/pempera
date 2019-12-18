package com.company.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class MainFrame extends Frame implements WindowListener, ActionListener {
    private JButton makeOrderButton, loadOrderButton, exitButton;
    MakeOrderFrame orderWindow;

    public static void main(String[] args){
        MainFrame mainWindow = new MainFrame();
        mainWindow.setSize(340,75);
        mainWindow.setLocation(400,400);
        mainWindow.setVisible(true);
    }

    public MainFrame(){
        super("Main Window");
        setLayout(new FlowLayout());
        addWindowListener(this);
        makeOrderButton = new JButton("Make order");
        loadOrderButton = new JButton("Load order from database");
        exitButton = new JButton("Exit");
        add(makeOrderButton);
        add(loadOrderButton);
        add(exitButton);
        makeOrderButton.addActionListener(this);
        loadOrderButton.addActionListener(this);
        exitButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Load order from database":
                //load
                break;
            case "Make order":
                orderWindow = new MakeOrderFrame();
                orderWindow.setSize(540,200);
                orderWindow.setLocation(400,450);
                orderWindow.setVisible(true);
                break;
            case "Exit":
                dispose();
                System.exit(0);
        }
    }

    public void windowClosing(WindowEvent e) {
        dispose();
        System.exit(0);
    }

    public void windowOpened(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
}
