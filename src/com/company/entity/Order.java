package com.company.entity;

import com.company.entity.enums.PlateMaterialType;
import com.company.entity.enums.PlateShape;
import com.company.entity.enums.PlateThickness;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    PlateShape plateShape;
    PlateMaterialType plateMaterialType;
    PlateThickness plateThickness;
    Dimensions dimensions;

    public Order(){

    }

    public Order(PlateShape plateShape, PlateMaterialType plateMaterialType,
                 PlateThickness plateThickness, Dimensions dimensions){
        this.plateShape = plateShape;
        this.plateMaterialType=plateMaterialType;
        this.plateThickness=plateThickness;
        this.dimensions=new Dimensions (dimensions);
    }

    public Order(Order order){
        this(order.getPlateShape(), order.getPlateMaterialType(),
                order.getPlateThickness(), order.getDimensions());
    }
}
