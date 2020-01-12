package com.company.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MainFrame extends Frame implements WindowListener, ActionListener {
    private JButton makeOrderButton, loadOrdersButton, exitButton;
    private MakeOrderFrame makeOrderWindow;
    private ProcessOrdersFrame processOrdersWindow;

    public static void main(String[] args){
        MainFrame mainWindow = new MainFrame();
        mainWindow.setSize(400,75);
        mainWindow.setLocation(400,400);
        mainWindow.setVisible(true);
    }

    public MainFrame(){
        super("Main Window");
        setLayout(new FlowLayout());
        addWindowListener(this);
        makeOrderButton = new JButton("Make order");
        loadOrdersButton = new JButton("Process multiple orders");
        exitButton = new JButton("Exit");
        add(makeOrderButton);
        add(loadOrdersButton);
        add(exitButton);
        makeOrderButton.addActionListener(this);
        loadOrdersButton.addActionListener(this);
        exitButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Process multiple orders":
                this.setEnabled(false);
                processOrdersWindow = new ProcessOrdersFrame();
                processOrdersWindow.setSize(480,200);
                processOrdersWindow.setLocation(400,450);
                processOrdersWindow.setVisible(true);
                processOrdersWindow.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        MainFrame.this.setEnabled(true);
                        MainFrame.this.toFront();
                    }});
                break;
            case "Make order":
                this.setEnabled(false);
                makeOrderWindow = new MakeOrderFrame();
                makeOrderWindow.setSize(480,200);
                makeOrderWindow.setLocation(400,450);
                makeOrderWindow.setVisible(true);
                makeOrderWindow.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        MainFrame.this.setEnabled(true);
                        MainFrame.this.toFront();
                    }});
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
