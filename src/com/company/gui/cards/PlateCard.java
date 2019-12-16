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

    public void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,4000/3,2000/3);
        g.setColor(Color.BLACK);
        g.drawRect(0,0,4000/3,2000/3);
        for (ElementOnPlate e : plate.elements){
            g.drawRect(e.getPos_y().intValue()/3,e.getPos_x().intValue()/3,
                    e.getElement().getDimensions().getDimension_Y().intValue()/3,
                    e.getElement().getDimensions().getDimension_X().intValue()/3);
        }

    }
}
