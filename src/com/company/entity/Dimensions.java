package com.company.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dimensions {
    private Double X_dimension;
    private Double y_dimension;

    public Dimensions() {

    }

    public Dimensions(Double x, Double y) {
        this.X_dimension = x;
        this.y_dimension = y;
    }

    public Dimensions(Dimensions dimensions){
        this(dimensions.getX_dimension(), dimensions.getY_dimension());
    }
}
