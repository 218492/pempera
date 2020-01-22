package com.company.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MainFrame extends Frame implements WindowListener, ActionListener {
    private JButton makeOrderButton, loadOrdersButton, modifyOrderButton, exitButton;
    private MakeOrderFrame makeOrderWindow;
    private ProcessOrdersFrame processOrdersWindow;
    private ModifyOrDeleteOrderFrame modifyWindow;

    public static void main(String[] args){
        MainFrame mainWindow = new MainFrame();
        mainWindow.setSize(520,75);
        mainWindow.setLocation(400,400);
        mainWindow.setVisible(true);
    }

    public MainFrame(){
        super("Main Window");
        setLayout(new FlowLayout());
        addWindowListener(this);
        makeOrderButton = new JButton("Make order");
        loadOrdersButton = new JButton("Process multiple orders");
        modifyOrderButton = new JButton("Modify single order");
        exitButton = new JButton("Exit");
        add(makeOrderButton);
        add(loadOrdersButton);
        add(modifyOrderButton);
        add(exitButton);
        makeOrderButton.addActionListener(this);
        loadOrdersButton.addActionListener(this);
        modifyOrderButton.addActionListener(this);
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

            case "Modify single order":
                this.setEnabled(false);
                modifyWindow = new ModifyOrDeleteOrderFrame();
                modifyWindow.setSize(480,220);
                modifyWindow.setLocation(400,450);
                modifyWindow.setVisible(true);
                modifyWindow.addWindowListener(new WindowAdapter() {
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
