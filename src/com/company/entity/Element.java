package com.company.entity;

import com.company.entity.enums.PlateMaterialType;
import com.company.entity.enums.PlateShape;
import com.company.entity.enums.PlateThickness;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Element {
    PlateShape plateShape;
    PlateMaterialType plateMaterialType;
    PlateThickness plateThickness;
    Dimensions dimensions;

    public Element(){

    }

    public Element(PlateShape plateShape, PlateMaterialType plateMaterialType,
                   PlateThickness plateThickness, Dimensions dimensions){
        this.plateShape = plateShape;
        this.plateMaterialType=plateMaterialType;
        this.plateThickness=plateThickness;
        this.dimensions=new Dimensions (dimensions);
    }

    public Element(Element element){
        this(element.getPlateShape(), element.getPlateMaterialType(),
                element.getPlateThickness(), element.getDimensions());
    }
}
