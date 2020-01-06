package com.company.algorithm;

import com.company.entity.ElementWithQuantity;
import com.company.entity.enums.DimensionsElement;

import java.util.Comparator;

public class ElementSorter implements Comparator<ElementWithQuantity> {

    DimensionsElement option;

    public ElementSorter(DimensionsElement option){
        this.option = option;
    }

    public int compare(ElementWithQuantity o1, ElementWithQuantity o2){
        int comp = 0;
        if (this.option == DimensionsElement.BY_X)
            comp = byX(o1,o2);
        if (this.option == DimensionsElement.BY_Y)
            comp = byY(o1,o2);
        return comp;
    }

    public int byX(ElementWithQuantity o1, ElementWithQuantity o2){
        double this_x, other_x;
        this_x = o1.getElement().getDimensions().getDimension_X();
        other_x = o2.getElement().getDimensions().getDimension_X();
        return this_x > other_x ? -1 : this_x < other_x ? 1 : 0;
    }

    public int byY(ElementWithQuantity o1, ElementWithQuantity o2){
        double this_y, other_y;
        this_y = o1.getElement().getDimensions().getDimension_Y();
        other_y = o2.getElement().getDimensions().getDimension_Y();
        return this_y > other_y ? -1 : this_y < other_y ? 1 : 0;
    }
}
