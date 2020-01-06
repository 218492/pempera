package com.company.gui;

import com.company.entity.Dimensions;
import com.company.entity.ElementWithQuantity;
import com.company.entity.Element;
import com.company.entity.enums.PlateMaterialType;
import com.company.entity.enums.PlateShape;
import com.company.entity.enums.PlateThickness;
import com.company.gui.cards.RectanglePanel;
import com.company.gui.cards.TriangleEquiPanel;
import com.company.gui.cards.TriangleRectPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddComponentFrame extends JDialog implements WindowListener, ActionListener, ItemListener{

    private JPanel shapeSelect;
    private JButton addComponent;
    private ElementWithQuantity elementWithQuantity;
    private SpinnerListModel quantitySpinnerModel;
    private JSpinner quantity;

    public static void main(String[] args){
        AddComponentFrame orderWindow = new AddComponentFrame();
        orderWindow.setSize(440,175);
        orderWindow.setLocation(400,450);
        orderWindow.setVisible(true);
    }

    public AddComponentFrame(){
        //super("Add Component");
        setModal(true);

        addWindowListener(this);
        JPanel rectanglePanel = new RectanglePanel();
        JPanel triangleRectPanel = new TriangleRectPanel();
        JPanel triangleEquiPanel = new TriangleEquiPanel();

        String[] quantityNumbers = new String[99];
        getQuantityNumber(quantityNumbers);
        quantitySpinnerModel= new SpinnerListModel(quantityNumbers);
        quantity = new JSpinner(quantitySpinnerModel);
        quantity.setPreferredSize(new Dimension(50,25));

        shapeSelect = new JPanel(new CardLayout());
        shapeSelect.add(rectanglePanel, "Rectangle");
        shapeSelect.add(triangleRectPanel, "Rectangle triangle");
        shapeSelect.add(triangleEquiPanel, "Rectangle equi");
        JPanel comboBoxShape = new JPanel();
        String[] comboBoxItems = {"Rectangle","Rectangle triangle","Rectangle equi" };
        JComboBox shapeChoose = new JComboBox(comboBoxItems);
        shapeChoose.setEditable(false);
        shapeChoose.addItemListener(this);
        comboBoxShape.add(shapeChoose);
        comboBoxShape.add(quantity);

        add(comboBoxShape, BorderLayout.PAGE_START);
        add(shapeSelect, BorderLayout.CENTER);
        addComponent = new JButton("Add component");
        addComponent.addActionListener(this);
        add(addComponent, BorderLayout.PAGE_END);
    }

    public ElementWithQuantity getOrder(){
        return elementWithQuantity;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Add component"){

            JPanel shapeCard = null;
            for (Component comp : shapeSelect.getComponents()) {
                if (comp.isVisible() == true) {
                    shapeCard = (JPanel) comp;
                }
            }
             Element element = new Element();

            String shape = shapeCard.toString();
            String[] data = shape.split("[,]");
            shape = data[0];
            data = shape.split("[.]");
            shape = data[data.length-1];
            shape = shape.replace("[","");
            String dimension = shapeCard.getComponent(0).toString();
            data = dimension.split("[=]");
            dimension = data[data.length-1];
            dimension = dimension.replace("]","");
            String thickness = shapeCard.getComponent(1).toString();
            data = thickness.split("[=]");
            thickness = data[data.length-1];
            thickness = thickness.replace("]","");
            String material = shapeCard.getComponent(2).toString();
            data = material.split("[=]");
            material = data[data.length-1];
            material = material.replace("]","");

            switch (shape){
                case "RectanglePanel":
                    element.setPlateShape(PlateShape.RECTANGLE);
                    break;
                case "TriangleEquiPanel":
                    element.setPlateShape(PlateShape.TRIANGLE_EQUI);
                    break;
                case "TriangleRectPanel":
                    element.setPlateShape(PlateShape.TRIANGLE_RECT);
                    break;
            }

            switch(material){
                case "Stainless steel":
                    element.setPlateMaterialType(PlateMaterialType.STAINLESS_STEEL);
                    break;
                case "Aluminium":
                    element.setPlateMaterialType(PlateMaterialType.ALUMINIUM);
                    break;
                case "Galvanised":
                    element.setPlateMaterialType(PlateMaterialType.GALVANISED);
                    break;
            }

            switch (thickness){
                case "Thick":
                    element.setPlateThickness(PlateThickness.THICK);
                    break;
                case "Thin":
                    element.setPlateThickness(PlateThickness.THIN);
                    break;
            }

            data = dimension.split("[x]");
            element.setDimensions(new Dimensions(Double.parseDouble(data[0]),Double.parseDouble(data[1])));
            elementWithQuantity = new ElementWithQuantity();
            elementWithQuantity.setElement(element);
            String quant = quantity.getValue().toString();
            elementWithQuantity.setQuantity(Integer.valueOf(quant));
            setVisible(false);

        }
    }

    private void getQuantityNumber(String[] q) {
        for (Integer i =1; i<100; i++){
            q[i-1] = new String(i.toString());
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
        CardLayout c1 =(CardLayout)(shapeSelect.getLayout());
        c1.show(shapeSelect, (String)e.getItem());
    }
}
