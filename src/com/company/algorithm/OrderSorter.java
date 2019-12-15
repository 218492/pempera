package com.company.algorithm;

import com.company.entity.OrderWithQuantity;
import com.company.entity.enums.DimensionsOrder;

import java.util.Comparator;

public class OrderSorter implements Comparator<OrderWithQuantity> {

    DimensionsOrder option;

    public OrderSorter(DimensionsOrder option){
        this.option = option;
    }

    public int compare(OrderWithQuantity o1, OrderWithQuantity o2){
        int comp = 0;
        if (this.option == DimensionsOrder.BY_X)
            comp = byX(o1,o2);
        if (this.option == DimensionsOrder.BY_Y)
            comp = byY(o1,o2);
        return comp;
    }

    public int byX(OrderWithQuantity o1, OrderWithQuantity o2){
        double this_x, other_x;
        this_x = o1.getOrder().getDimensions().getDimension_X();
        other_x = o2.getOrder().getDimensions().getDimension_X();
        return this_x > other_x ? -1 : this_x < other_x ? 1 : 0;
    }

    public int byY(OrderWithQuantity o1, OrderWithQuantity o2){
        double this_y, other_y;
        this_y = o1.getOrder().getDimensions().getDimension_Y();
        other_y = o2.getOrder().getDimensions().getDimension_Y();
        return this_y > other_y ? -1 : this_y < other_y ? 1 : 0;
    }
}
