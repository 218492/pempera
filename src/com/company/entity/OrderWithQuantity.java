package com.company.entity;

import com.company.algorithm.OrderSorter;
import com.company.entity.enums.DimensionsOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderWithQuantity {
    Order order;
    Integer quantity;
    public static OrderSorter SORT_BY_X = new OrderSorter(DimensionsOrder.BY_X);
    public static OrderSorter SORT_BY_Y = new OrderSorter(DimensionsOrder.BY_Y);

    public OrderWithQuantity(){

    }

    public OrderWithQuantity(Order o, Integer q){
        this.order = new Order(o);
        this.quantity = q;
    }

    public OrderWithQuantity(OrderWithQuantity copy){
        this(copy.getOrder(),copy.getQuantity());
    }
}
