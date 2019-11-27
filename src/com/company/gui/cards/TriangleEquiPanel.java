package com.company.gui.cards;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TriangleEquiPanel extends CardPanel implements ActionListener {
    public static String[] dimensions = {"10x10"};

    public void main(String[] args) {
        TriangleEquiPanel field = new TriangleEquiPanel();
        field.setVisible(true);
    }

    public TriangleEquiPanel(){
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
