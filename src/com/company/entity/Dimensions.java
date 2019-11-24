package com.company.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dimensions {
    private Double dimension_X;
    private Double dimension_Y;

    public Dimensions() {

    }

    public Dimensions(Double x, Double y) {
        this.dimension_X = x;
        this.dimension_Y = y;
    }
}
