package com.company.gui.cards;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RectanglePanel extends CardPanel implements ActionListener {
    public static String[] dimensions = {"1800x600", "3000x600", "3500x600", "490x600", "400x200", "300x70", "1800x80"};

    public void main(String[] args) {
        RectanglePanel field = new RectanglePanel();
        field.setVisible(true);
    }

    public RectanglePanel(){
        dimensionList = new JComboBox(dimensions);
        dimensionList.addActionListener(this);

        thicknessList = new JComboBox(thickness);
        thicknessList.addActionListener(this);

        materialList = new JComboBox(material);
        materialList.addActionListener(this);

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        add(dimensionList);
        add(thicknessList);
        add(materialList);

    }

    public void actionPerformed(ActionEvent e){

    }
}
