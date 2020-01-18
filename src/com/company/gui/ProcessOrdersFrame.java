package com.company.gui;

import com.company.entity.ElementWithQuantity;
import com.company.entity.Order;
import com.company.globaloperations.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ProcessOrdersFrame extends Frame implements WindowListener, ActionListener {

    private DefaultListModel listModel;
    private JButton addComponentButton, deleteComponentButton, processOrders, cancelButton, saveNewOrder;
    private JList componentsList;
    private JTextField newOrderName;
    private JLayeredPane topPane, centralList, centralPane;
    private JLabel componentsListLabel, newOrderNameLabel;
    private JScrollPane scrollList;
    private AddElementFrame addElementWindow;
    private List<Order> elementsList = new ArrayList<>();

    private static final String TEMPLATE_ORDERNAME = "type filename";

    //TODO WASILEWSKI addNewOrder
    private void addNewElement(ElementWithQuantity elementWithQuantity) {
//        if (elementWithQuantity != null) {
//            String elementToDisplay = ElementAssembler.convertElementWithQuantityToString(elementWithQuantity);
//            elementsList.add(elementWithQuantity);
//            listModel.addElement(elementToDisplay);
//        }
    }

    ProcessOrdersFrame() {
        super("Process multiple orders");
        setLayout(new BorderLayout());
        addWindowListener(this);

        newOrderName = new JTextField(TEMPLATE_ORDERNAME, 20);
        newOrderNameLabel = new JLabel("Order Name:");
        topPane = new JLayeredPane();
        topPane.setLayout(new FlowLayout());

        topPane.add(newOrderNameLabel);
        topPane.add(newOrderName);
        add(topPane, BorderLayout.PAGE_START);

        componentsListLabel = new JLabel("Orders to process:");
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
            addNewElement(addElementWindow.getElementWithQuantity());
        });
        saveNewOrder.addActionListener(e -> saveToDatabase());
        deleteComponentButton.addActionListener(e -> deleteOrder());
        processOrders.addActionListener(e -> bestFitAndDraw());
        cancelButton.addActionListener(e -> dispose());
        newOrderName.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e) {
                if(newOrderName.getText().equals(TEMPLATE_ORDERNAME)){
                    newOrderName.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
    }

    private void deleteOrder() {
        int selectedIndex = componentsList.getSelectedIndex();
        if (selectedIndex != -1) {
            listModel.remove(selectedIndex);
            elementsList.remove(selectedIndex);
        }
    }


    private void addButtonAction() {
        newAddWindow();
    }

    //TODO poprawic
    private void bestFitAndDraw() {
//        java.util.List<ElementWithQuantity> temp = new Vector<>();
//        for (ElementWithQuantity o : elementsList) {
//            temp.add(new ElementWithQuantity(o));
//        }
//        BestFit nowy = new BestFit(temp);
//
//        OutputPlatesDrawing drawings = new OutputPlatesDrawing(nowy.getPlates());
//        drawings.setSize(1355, 745);
//        drawings.setLocation(0, 0);
//        drawings.setVisible(true);
    }
    //TODO konwersja ORDER na ELEMENTS WITH QUANTITY
    private void saveToDatabase(){
        String ordName = newOrderName.getText();
        DatabaseManager databaseManager = new DatabaseManager();
        List<ElementWithQuantity> elementsListToSave = new Vector<>();

        databaseManager.saveFullOrderToDatabase(elementsListToSave, ordName);
    }

    public void actionPerformed(ActionEvent e) {
    }

    private void newAddWindow() {
        addElementWindow = new AddElementFrame();
        addElementWindow.setSize(440, 175);
        addElementWindow.setLocation(400, 450);
        addElementWindow.addWindowListener(this);
        addElementWindow.setVisible(true);
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
        addComponentButton = new JButton("Add order");
        deleteComponentButton = new JButton("Delete order");
        saveNewOrder = new JButton("Save as new order");
        processOrders = new JButton("Proccess order");
        cancelButton = new JButton("Cancel");
        GridBagConstraints c = new GridBagConstraints();
        JPanel panel = new JPanel(new GridBagLayout());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(addComponentButton, c);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(deleteComponentButton, c);

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(processOrders, c);

        c.gridx = 0;
        c.gridy = 3;
        panel.add(saveNewOrder, c);

        c.gridx = 0;
        c.gridy = 4;
        panel.add(cancelButton, c);
        return panel;
    }
}
