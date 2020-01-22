package com.company.gui;

import com.company.entity.ElementWithQuantity;
import com.company.entity.Order;
import com.company.globaloperations.DatabaseManager;
import com.company.globaloperations.ElementAssembler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

public class ModifyOrDeleteOrderFrame extends Frame implements WindowListener, ActionListener {

    private DefaultListModel listModel;
    private JTextField orderName;
    private JLabel orderNameLabel, componentsListLabel;
    private JList componentsList;
    private JScrollPane scrollList;
    private JLayeredPane topPane, centralList, centralPane;
    private AddOrderFrame selectOrderWindow;
    private AddElementFrame newComponentWindow;
    private JButton selectOrderButton, deleteComponentButton, deleteOrder, cancelButton, saveOrder, addComponentButton;
    private List<ElementWithQuantity> elementsList = new ArrayList<>();

    private static final String TEMPLATE_ORDERNAME = "filename";

    private void selectOrder(Order order){
        if (order != null) {
            orderName.setText(order.getName());
            DatabaseManager databaseManager = new DatabaseManager();
            List<ElementWithQuantity> temp = databaseManager.getElementsListFromOrder(order.getId());
            listModel.clear();
            elementsList.clear();
            for(ElementWithQuantity e : temp) {
                String elemStr = ElementAssembler.convertElementWithQuantityToString(e); //
                listModel.addElement(elemStr);
            }
            elementsList.addAll(temp);
        }
    }

    ModifyOrDeleteOrderFrame() {
        super("Process multiple orders");
        setLayout(new BorderLayout());
        addWindowListener(this);

        orderName = new JTextField(TEMPLATE_ORDERNAME, 20);
        orderNameLabel = new JLabel("Order Name:");
        topPane = new JLayeredPane();
        topPane.setLayout(new FlowLayout());

        topPane.add(orderNameLabel);
        topPane.add(orderName);
        add(topPane, BorderLayout.PAGE_START);

        componentsListLabel = new JLabel("Elements list:");
        listModel = new DefaultListModel<String>();
        componentsList = new JList(listModel);
        centralPane = new JLayeredPane();
        centralPane.setLayout(new BoxLayout(centralPane, BoxLayout.PAGE_AXIS));
        centralList = new JLayeredPane();
        centralList.setLayout(new FlowLayout(FlowLayout.LEFT));
        centralList.add(componentsList);
        scrollList = new JScrollPane(centralList);
        centralPane.add(componentsListLabel);
        centralPane.add(scrollList);
        add(centralPane, BorderLayout.CENTER);

        add(setButtonLocation(), BorderLayout.LINE_START);
        addComponentButton.addActionListener(e -> {
            addButtonAction();
            addNewElement(newComponentWindow.getElementWithQuantity());
        });
        selectOrderButton.addActionListener(e -> {
            selectOrderButton();
            selectOrder(selectOrderWindow.getOrder());
        });
        saveOrder.addActionListener(e -> saveToDatabase());
        deleteComponentButton.addActionListener(e -> deleteElement());
        deleteOrder.addActionListener(e -> deleteOrder());
        cancelButton.addActionListener(e -> dispose());
    }

    private void addButtonAction() {
        newComponentWindow();
    }

    private void newComponentWindow() {
        newComponentWindow = new AddElementFrame();
        newComponentWindow.setSize(440, 175);
        newComponentWindow.setLocation(400, 450);
        newComponentWindow.addWindowListener(this);
        newComponentWindow.setVisible(true);
    }

    private void addNewElement(ElementWithQuantity elementWithQuantity) {
        if (elementWithQuantity != null) {
            String elementToDisplay = ElementAssembler.convertElementWithQuantityToString(elementWithQuantity);
            elementsList.add(elementWithQuantity);
            listModel.addElement(elementToDisplay);
        }
    }

    private void selectOrderButton() {
        newSelectWindow();
    }

    private void  deleteOrder(){
        String ordName = orderName.getText();
        DatabaseManager databaseManager = new DatabaseManager();
        Order temp = databaseManager.findOrder(ordName);
        if ( temp != null){
            databaseManager.removeElementOrderConnections(temp.getId());
            databaseManager.removeOrder(temp.getId());
            orderName.setText(TEMPLATE_ORDERNAME);
        }
    }


    private void deleteElement() {
        int selectedIndex = componentsList.getSelectedIndex();
        if (selectedIndex != -1) {
            listModel.remove(selectedIndex);
            elementsList.remove(selectedIndex);
        }
    }

    private void saveToDatabase(){
        String ordName = orderName.getText();
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.saveFullOrderToDatabase(elementsList, ordName);
    }

    public void actionPerformed(ActionEvent e) {
    }

    private void newSelectWindow() {
        selectOrderWindow = new AddOrderFrame();
        selectOrderWindow.setSize(340, 100);
        selectOrderWindow.setLocation(400, 450);
        selectOrderWindow.addWindowListener(this);
        selectOrderWindow.setVisible(true);
    }

    public void windowClosing(WindowEvent e) {
        if(e.getSource() instanceof ProcessOrdersFrame){
            dispose();
        }
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    private JPanel setButtonLocation() {
        addComponentButton = new JButton("Add element");
        selectOrderButton = new JButton("Select order");
        deleteComponentButton = new JButton("Delete element");
        saveOrder = new JButton("Save order");
        deleteOrder = new JButton("Delete order");
        cancelButton = new JButton("Cancel");
        GridBagConstraints c = new GridBagConstraints();
        JPanel panel = new JPanel(new GridBagLayout());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(selectOrderButton, c);
        c.gridx = 0;
        c.gridy = 1;

        panel.add(addComponentButton, c);
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(deleteOrder, c);

        c.gridx = 0;
        c.gridy = 3;
        panel.add(saveOrder, c);

        c.gridx = 0;
        c.gridy = 4;
        panel.add(deleteComponentButton, c);

        c.gridx = 0;
        c.gridy = 5;
        panel.add(cancelButton, c);
        return panel;
    }
}
