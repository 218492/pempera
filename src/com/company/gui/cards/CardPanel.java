package com.company.gui.cards;

import javax.swing.*;

public class CardPanel extends JPanel {
    static String[] material = {"Stainless steel", "Aluminium", "Galvanised"};
    static String[] thickness = {"Thick", "Thin"};
    JComboBox dimensionList;
    JComboBox thicknessList;
    JComboBox materialList;

    public String getDim(){
        return dimensionList.getSelectedItem().toString();
    }

    public String getThi(){
        return thicknessList.getSelectedItem().toString();
    }

    public String getMat(){
        return materialList.getSelectedItem().toString();
    }
}
