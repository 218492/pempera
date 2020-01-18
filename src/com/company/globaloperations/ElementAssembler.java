package com.company.globaloperations;

import com.company.entity.Dimensions;
import com.company.entity.Element;
import com.company.entity.ElementWithQuantity;
import com.company.entity.enums.PlateMaterialType;
import com.company.entity.enums.PlateShape;
import com.company.entity.enums.PlateThickness;
import com.company.exception.WrongDataException;

import java.util.Arrays;

public class ElementAssembler {
    public static Element assembleElement(String[] orderAsStringArray) throws WrongDataException {
        Element element = new Element();
        try {
            element.setDimensions(new Dimensions(Double.valueOf(orderAsStringArray[0]), Double.valueOf(orderAsStringArray[1])));
            element.setPlateShape(Enum.valueOf(PlateShape.class, orderAsStringArray[2]));
            element.setPlateMaterialType(Enum.valueOf(PlateMaterialType.class, orderAsStringArray[3]));
            element.setPlateThickness(Enum.valueOf(PlateThickness.class, orderAsStringArray[4]));
        } catch (Exception e) {
            throw new WrongDataException();
        }
        return element;
    }

    public static String[] convertElementToStringArray(Element element) {
        String[] elementAsStringArray = new String[5];

        elementAsStringArray[0] = element.getDimensions().getX_dimension().toString();
        elementAsStringArray[1] = element.getDimensions().getY_dimension().toString();
        elementAsStringArray[2] = element.getPlateShape().toString();
        elementAsStringArray[3] = element.getPlateMaterialType().toString();
        elementAsStringArray[4] = element.getPlateThickness().toString();

        return elementAsStringArray;
    }

    public static String convertElementWithQuantityToString(ElementWithQuantity elementWithQuantity) {
        String[] elementAsStringArray = new String[6];
        Element element = elementWithQuantity.getElement();

        elementAsStringArray[0] = elementWithQuantity.getQuantity().toString();
        elementAsStringArray[1] = element.getDimensions().getX_dimension().toString();
        elementAsStringArray[2] = element.getDimensions().getY_dimension().toString();
        elementAsStringArray[3] = element.getPlateShape().toString();
        elementAsStringArray[4] = element.getPlateMaterialType().toString();
        elementAsStringArray[5] = element.getPlateThickness().toString();

        return Arrays.toString(elementAsStringArray);
    }
}
