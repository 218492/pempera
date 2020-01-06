package com.company.globaloperations;

import com.company.entity.Dimensions;
import com.company.entity.Element;
import com.company.entity.enums.PlateMaterialType;
import com.company.entity.enums.PlateShape;
import com.company.entity.enums.PlateThickness;
import com.company.exception.WrongDataException;

public class ElementAssembler {
    public static Element assembleOrder(String[] orderAsStringArray) throws WrongDataException {
        Element element = new Element();
        try {
            element.setDimensions(new Dimensions(Double.valueOf(orderAsStringArray[0]), Double.valueOf(orderAsStringArray[1])));
            element.setPlateShape(Enum.valueOf(PlateShape.class, orderAsStringArray[2]));
            element.setPlateThickness(Enum.valueOf(PlateThickness.class, orderAsStringArray[3]));
            element.setPlateMaterialType(Enum.valueOf(PlateMaterialType.class, orderAsStringArray[4]));
        } catch (Exception e) {
            throw new WrongDataException();
        }
        return element;
    }
}
