package com.company.gui;

import com.company.algorithm.BestFit;
import com.company.entity.ElementWithQuantity;
import com.company.globaloperations.DatabaseManager;
import com.company.globaloperations.ElementAssembler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MakeOrderFrame extends Frame implements WindowListener, ActionListener {

    private DefaultListModel listModel;
    private JButton addComponentButton, deleteComponentButton, saveOrder, processOrder, cancelButton;
    private JTextField orderName;
    private JList componentsList;
    private JLayeredPane topPane, centralList, centralPane;
    private JLabel orderNameLabel, componentsListLabel;
    private JScrollPane scrollList;
    private AddElementFrame newComponentWindow;
    private List<ElementWithQuantity> elementsList = new ArrayList<>();

    private static final String TEMPLATE_ORDERNAME = "type filename";

    private void addNewElement(ElementWithQuantity elementWithQuantity) {
        if (elementWithQuantity != null) {
            String elementToDisplay = ElementAssembler.convertElementWithQuantityToString(elementWithQuantity);
            elementsList.add(elementWithQuantity);
            listModel.addElement(elementToDisplay);
        }
    }

    MakeOrderFrame() {
        super("Order Edition");
        setLayout(new BorderLayout());
        addWindowListener(this);

        orderName = new JTextField(TEMPLATE_ORDERNAME, 20);
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
        deleteComponentButton.addActionListener(e -> deleteOrder());
        processOrder.addActionListener(e -> bestFitAndDraw());
        saveOrder.addActionListener(e -> saveToDatabase());
        cancelButton.addActionListener(e -> dispose());
        orderName.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e) {
                if(orderName.getText().equals(TEMPLATE_ORDERNAME)){
                    orderName.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

    }

    private void saveToDatabase(){
        String ordName = orderName.getText();
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.saveFullOrderToDatabase(elementsList, ordName);
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

    private void bestFitAndDraw() {
        java.util.List<ElementWithQuantity> temp = new Vector<>();
        for (ElementWithQuantity element : elementsList) {
            temp.add(new ElementWithQuantity(element));
        }
        BestFit nowy = new BestFit(temp);

        OutputPlatesDrawing drawings = new OutputPlatesDrawing(nowy.getPlates());
        drawings.setSize(1355, 745);
        drawings.setLocation(0, 0);
        drawings.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
    }

    private void newAddWindow() {
        newComponentWindow = new AddElementFrame();
        newComponentWindow.setSize(440, 175);
        newComponentWindow.setLocation(400, 450);
        newComponentWindow.addWindowListener(this);
        newComponentWindow.setVisible(true);
    }

    public void windowClosing(WindowEvent e) {
        if(e.getSource() instanceof MakeOrderFrame){
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
        deleteComponentButton = new JButton("Delete element");
        processOrder = new JButton("Proccess order");
        saveOrder = new JButton("Save order");
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
        panel.add(processOrder, c);

        c.gridx = 0;
        c.gridy = 3;
        panel.add(saveOrder, c);

        c.gridx = 0;
        c.gridy = 4;
        panel.add(cancelButton, c);
        return panel;
    }
}
