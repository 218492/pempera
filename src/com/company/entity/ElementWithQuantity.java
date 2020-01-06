package com.company.entity;

import com.company.algorithm.ElementSorter;
import com.company.entity.enums.DimensionsElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElementWithQuantity {
    Element element;
    Integer quantity;
    public static ElementSorter SORT_BY_X = new ElementSorter(DimensionsElement.BY_X);
    public static ElementSorter SORT_BY_Y = new ElementSorter(DimensionsElement.BY_Y);

    public ElementWithQuantity(){

    }

    public ElementWithQuantity(Element o, Integer q){
        this.element = new Element(o);
        this.quantity = q;
    }

    public ElementWithQuantity(ElementWithQuantity copy){
        this(copy.getElement(),copy.getQuantity());
    }
}
