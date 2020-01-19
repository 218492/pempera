package com.company.gui;

import com.company.entity.Order;
import com.company.globaloperations.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddOrderFrame extends JDialog implements WindowListener, ActionListener, ItemListener{

    private JPanel orderSelect;
    private JButton addComponent;
    private Order order;

    public static void main(String[] args){
        AddElementFrame orderWindow = new AddElementFrame();
        orderWindow.setSize(440,175);
        orderWindow.setLocation(400,450);
        orderWindow.setVisible(true);
    }

    public AddOrderFrame(){
        setModal(true);

        addWindowListener(this);
        DatabaseManager databaseManager = new DatabaseManager();

        JPanel comboBoxShape = new JPanel();
        String[] comboBoxItems = databaseManager.loadAllOrders();
        JComboBox orderChoose = new JComboBox(comboBoxItems);
        orderChoose.setEditable(false);
        orderChoose.addItemListener(this);
        comboBoxShape.add(orderChoose);

        add(comboBoxShape, BorderLayout.PAGE_START);
        addComponent = new JButton("Add order");
        addComponent.addActionListener(this);
        add(addComponent, BorderLayout.PAGE_END);
    }

    public Order getOrder(){
        return order;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add order")){
            setVisible(false);
        }
    }

    public void windowClosing(WindowEvent e) {
        dispose();
    }

    public void windowOpened(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}

    @Override
    public void itemStateChanged(ItemEvent e) {
        DatabaseManager databaseManager = new DatabaseManager();
        order = databaseManager.findOrder ((String)e.getItem());
    }
}


