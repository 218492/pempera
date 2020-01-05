package com.company.orderoperations;

import com.company.entity.Dimensions;
import com.company.entity.Order;
import com.company.entity.enums.PlateMaterialType;
import com.company.entity.enums.PlateShape;
import com.company.entity.enums.PlateThickness;
import com.company.exception.WrongDataException;

public class OrderAssembler {
    public static Order assembleOrder(String[] orderAsStringArray) throws WrongDataException {
        Order order = new Order();
        try {
            order.setDimensions(new Dimensions(Double.valueOf(orderAsStringArray[0]), Double.valueOf(orderAsStringArray[1])));
            order.setPlateShape(Enum.valueOf(PlateShape.class, orderAsStringArray[2]));
            order.setPlateThickness(Enum.valueOf(PlateThickness.class, orderAsStringArray[3]));
            order.setPlateMaterialType(Enum.valueOf(PlateMaterialType.class, orderAsStringArray[4]));
        } catch (Exception e) {
            throw new WrongDataException();
        }
        return order;
    }
}
