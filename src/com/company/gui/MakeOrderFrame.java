package com.company.gui;

import com.company.entity.OrderWithQuantity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class MakeOrderFrame extends Frame implements WindowListener, ActionListener {


    DefaultListModel listModel;
    JButton addComponentButton, deleteComponentButton, saveOrder, cancelOrder;
    JTextField orderName;
    JList componentsList;
    JLayeredPane topPane, centralPane, leftPane;
    JLabel orderNameLabel, componentsListLabel;
    AddComponentFrame newComponentWindow;


    public static void main(String[] args){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MakeOrderFrame orderWindow = new MakeOrderFrame();
                orderWindow.setSize(480,175);
                orderWindow.setLocation(400,450);
                orderWindow.setVisible(true);
            }
        });

    }

    public void addNewOrder(OrderWithQuantity o){
        String quantity = o.getQuantity().toString();
        String shape = o.getOrder().getPlateShape().toString();
        String material = o.getOrder().getPlateMaterialType().toString();
        String thickness = o.getOrder().getPlateThickness().toString();
        String dimension = o.getOrder().getDimensions().getDimension_X().toString() +"x" +o.getOrder().getDimensions().getDimension_Y().toString();
        String elementName = quantity + ", " + shape + ", " + material + ", " + thickness + ", " + dimension;
        listModel.addElement(elementName);
    }

    public MakeOrderFrame(){
        super("Order Edition");
        setLayout(new BorderLayout());
        addWindowListener(this);

        orderName = new JTextField("type filename",20);
        orderNameLabel = new JLabel("Order Name:");
        topPane = new JLayeredPane();
        topPane.setLayout(new FlowLayout());

        topPane.add(orderNameLabel);
        topPane.add(orderName);
        add(topPane, BorderLayout.PAGE_START);

        componentsListLabel = new JLabel("Elements in order:");
        listModel = new DefaultListModel<String>();
        componentsList = new JList(listModel);
        centralPane = new JLayeredPane();
        centralPane.setLayout(new BoxLayout(centralPane,BoxLayout.PAGE_AXIS));
        centralPane.add(componentsListLabel);
        centralPane.add(componentsList);

        add(centralPane, BorderLayout.CENTER);

        addComponentButton = new JButton("Add element");
        deleteComponentButton = new JButton("Delete element");
        saveOrder = new JButton("Save order");
        cancelOrder = new JButton("Cancel order");
        leftPane = new JLayeredPane();
        leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.PAGE_AXIS));

        addComponentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addButtonAction();
                addNewOrder(newComponentWindow.getOrder());
            }
        });
        deleteComponentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOrder();
            }
        });
        saveOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        });
        cancelOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        leftPane.add(addComponentButton);
        leftPane.add(deleteComponentButton);
        leftPane.add(saveOrder);
        leftPane.add(cancelOrder);
        add(leftPane, BorderLayout.LINE_START);
    }

    private void saveToFile() {
        String text = new String();
        for(int i = 0; i < listModel.size(); i++){
            text += listModel.elementAt(i).toString() + "\n";
        }
        PrintWriter out = null;
        try {
            out = new PrintWriter("orders/" +orderName.getText() + ".txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        out.println(text);
        out.close();
        dispose();
    }

    private void deleteOrder() {
        int selectedIndex = componentsList.getSelectedIndex();
        if (selectedIndex != -1) {
            listModel.remove(selectedIndex);
        }
    }


    private void addButtonAction() {
        newAddWindow();
    }

    public void actionPerformed(ActionEvent e) {
    }

    private void newAddWindow() {
        newComponentWindow = new AddComponentFrame();
        newComponentWindow.setSize(440,175);
        newComponentWindow.setLocation(400,450);
        newComponentWindow.addWindowListener(this);
        newComponentWindow.setVisible(true);
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
