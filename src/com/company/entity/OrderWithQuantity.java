package com.company.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderWithQuantity {
    Order order;
    Integer quantity;
}
