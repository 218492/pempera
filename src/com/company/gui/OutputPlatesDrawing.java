package com.company.gui;

import com.company.entity.Plate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

public class OutputPlatesDrawing extends Frame implements WindowListener, ActionListener {

    JPanel panelSelect;

    public static void main(String[] args){
        //OutputPlatesDrawing mainWindow = new OutputPlatesDrawing();
        //mainWindow.setSize(1000,1000);
        //mainWindow.setLocation(0,0);
        //mainWindow.setVisible(true);
    }

    public OutputPlatesDrawing(Vector<Plate> plates){
        super("Main Window");
        setLayout(new FlowLayout());
        addWindowListener(this);
        new JPanel(new CardLayout());

    }

    public void actionPerformed(ActionEvent e) {

    }
    public void windowOpened(WindowEvent e) {

    }
    public void windowClosing(WindowEvent e) {
        dispose();
        System.exit(0);
    }
    public void windowClosed(WindowEvent e) {

    }
    public void windowIconified(WindowEvent e) {

    }
    public void windowDeiconified(WindowEvent e) {

    }
    public void windowActivated(WindowEvent e) {

    }
    public void windowDeactivated(WindowEvent e) {

    }
}
