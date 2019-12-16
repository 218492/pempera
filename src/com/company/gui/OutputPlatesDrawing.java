package com.company.gui;

import com.company.algorithm.BestFit;
import com.company.entity.Dimensions;
import com.company.entity.Order;
import com.company.entity.OrderWithQuantity;
import com.company.entity.Plate;
import com.company.entity.enums.PlateMaterialType;
import com.company.entity.enums.PlateShape;
import com.company.entity.enums.PlateThickness;
import com.company.gui.cards.PlateCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Vector;

public class OutputPlatesDrawing extends Frame implements WindowListener, ActionListener,  ItemListener {

    JPanel panelSelect;
    List<JPanel> plateCards = new Vector<>();

    public static void main(String[] args){
        List<OrderWithQuantity> input = new Vector<>();
        Order smallRect = new Order();
        smallRect.setDimensions(new Dimensions(400.0, 150.0));
        smallRect.setPlateThickness(PlateThickness.THICK);
        smallRect.setPlateMaterialType(PlateMaterialType.GALVANISED);
        smallRect.setPlateShape(PlateShape.RECTANGLE);
        OrderWithQuantity smallRects = new OrderWithQuantity();
        smallRects.setOrder(smallRect);
        smallRects.setQuantity(45);
        input.add(smallRects);
        Order smallRect2 = new Order();
        smallRect2.setDimensions(new Dimensions(300.0, 600.0));
        smallRect2.setPlateThickness(PlateThickness.THICK);
        smallRect2.setPlateMaterialType(PlateMaterialType.GALVANISED);
        smallRect2.setPlateShape(PlateShape.RECTANGLE);
        OrderWithQuantity smallRects2 = new OrderWithQuantity();
        smallRects2.setOrder(smallRect2);
        smallRects2.setQuantity(45);
        input.add(smallRects2);
        Order smallRect3 = new Order();
        smallRect3.setDimensions(new Dimensions(350.0, 400.0));
        smallRect3.setPlateThickness(PlateThickness.THICK);
        smallRect3.setPlateMaterialType(PlateMaterialType.GALVANISED);
        smallRect3.setPlateShape(PlateShape.RECTANGLE);
        OrderWithQuantity smallRects3 = new OrderWithQuantity();
        smallRects3.setOrder(smallRect3);
        smallRects3.setQuantity(45);
        input.add(smallRects3);

        BestFit nowy = new BestFit(input);

        OutputPlatesDrawing mainWindow = new OutputPlatesDrawing(nowy.getPlates());
        mainWindow.setSize(1355,745);
        mainWindow.setLocation(0,0);
        mainWindow.setVisible(true);
    }

    public OutputPlatesDrawing(List<Plate> plates){
        super("Main Window");
        //setLayout(new FlowLayout());
        addWindowListener(this);
        panelSelect = new JPanel(new CardLayout());
        List<String> titles = new Vector<>();
        for (int i = 0; i< plates.size(); i++){
            titles.add("Plate: "+(i+1) +" - "+ plates.get(i).getMaterial().name() + ", "+ plates.get(i).getThickness().name());
        }
        int i =0;
        for ( Plate p:plates){
            panelSelect.add(new PlateCard(p), titles.get(i));
            i++;
        }
        JPanel comboBoxPlates = new JPanel();
        String[] comboBoxItems = titles.stream().toArray(String[]::new);
        JComboBox plateChoose = new JComboBox(comboBoxItems);
        plateChoose.setEditable(false);
        plateChoose.addItemListener(this);
        comboBoxPlates.add(plateChoose);
        //panelSelect.setPreferredSize(new Dimension(2000/2,4000/2));
        //JScrollPane scrollContent = new JScrollPane(panelSelect);
        //panelSelect.setAutoscrolls(true);
        add(comboBoxPlates, BorderLayout.PAGE_START);
        add(panelSelect, BorderLayout.CENTER);
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        CardLayout c1 =(CardLayout)(panelSelect.getLayout());
        c1.show(panelSelect, (String)e.getItem());

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
