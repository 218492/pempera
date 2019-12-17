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
}
