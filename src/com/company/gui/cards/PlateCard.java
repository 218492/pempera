package com.company.gui.cards;

import com.company.entity.ElementOnPlate;
import com.company.entity.Plate;

import javax.swing.*;
import java.awt.*;

public class PlateCard extends JPanel {

    Plate plate;

    public PlateCard(Plate p){
        this.plate = p;
        repaint();
    }

    public void paint(Graphics g){
        g.setColor(Color.BLACK);
        for (ElementOnPlate e : plate.elements){
            g.drawRect(e.getPos_x().intValue(),e.getPos_y().intValue(), e.getElement().getDimensions().getDimension_X().intValue(), e.getElement().getDimensions().getDimension_Y().intValue());
        }
    }
}
