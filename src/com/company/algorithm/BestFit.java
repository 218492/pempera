package com.company.algorithm;

import com.company.entity.*;
import com.company.entity.enums.PlateMaterialType;
import com.company.entity.enums.PlateShape;
import com.company.entity.enums.PlateThickness;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

@Getter
@Setter

public class BestFit {
    List<Plate> plates = new Vector<>();
    List<ElementWithQuantity> galvElem;
    Integer[] drawLine = new Integer[2000]; //4000 x 2000
    Integer maxLine = 4000;
    Integer padding = 5;
    public static void  main(String[] args){
        List<ElementWithQuantity> input = new Vector<>();
        Element smallRect = new Element();
        smallRect.setDimensions(new Dimensions(100.0, 200.0));
        smallRect.setPlateThickness(PlateThickness.THICK);
        smallRect.setPlateMaterialType(PlateMaterialType.GALVANISED);
        smallRect.setPlateShape(PlateShape.RECTANGLE);
        ElementWithQuantity smallRects = new ElementWithQuantity();
        smallRects.setElement(smallRect);
        smallRects.setQuantity(10);
        input.add(smallRects);
        Element smallRect2 = new Element();
        smallRect2.setDimensions(new Dimensions(300.0, 100.0));
        smallRect2.setPlateThickness(PlateThickness.THICK);
        smallRect2.setPlateMaterialType(PlateMaterialType.GALVANISED);
        smallRect2.setPlateShape(PlateShape.RECTANGLE);
        ElementWithQuantity smallRects2 = new ElementWithQuantity();
        smallRects2.setElement(smallRect2);
        smallRects2.setQuantity(10);
        input.add(smallRects2);

        BestFit nowy = new BestFit(input);
        nowy.getPlates();
    }

    public BestFit(List<ElementWithQuantity> inputList){
        galvElem = inputList.stream().filter(p -> p.getElement().getPlateMaterialType() == PlateMaterialType.GALVANISED && p.getElement().getPlateThickness() == PlateThickness.THICK).collect(Collectors.toList());
        if (!galvElem.isEmpty())
            placeElementsOnPlates( PlateMaterialType.GALVANISED, PlateThickness.THICK);

        galvElem = inputList.stream().filter(p -> p.getElement().getPlateMaterialType() == PlateMaterialType.GALVANISED && p.getElement().getPlateThickness() == PlateThickness.THIN).collect(Collectors.toList());
        if (!galvElem.isEmpty())
            placeElementsOnPlates(PlateMaterialType.GALVANISED, PlateThickness.THIN);

        galvElem = inputList.stream().filter(p -> p.getElement().getPlateMaterialType() == PlateMaterialType.STAINLESS_STEEL && p.getElement().getPlateThickness() == PlateThickness.THIN).collect(Collectors.toList());
        if (!galvElem.isEmpty())
            placeElementsOnPlates(PlateMaterialType.STAINLESS_STEEL, PlateThickness.THIN);

        galvElem = inputList.stream().filter(p -> p.getElement().getPlateMaterialType() == PlateMaterialType.STAINLESS_STEEL && p.getElement().getPlateThickness() == PlateThickness.THICK).collect(Collectors.toList());
        if (!galvElem.isEmpty())
            placeElementsOnPlates( PlateMaterialType.STAINLESS_STEEL, PlateThickness.THICK);

        galvElem = inputList.stream().filter(p -> p.getElement().getPlateMaterialType() == PlateMaterialType.ALUMINIUM && p.getElement().getPlateThickness() == PlateThickness.THIN).collect(Collectors.toList());
        if (!galvElem.isEmpty())
            placeElementsOnPlates(PlateMaterialType.ALUMINIUM, PlateThickness.THIN);

        galvElem = inputList.stream().filter(p -> p.getElement().getPlateMaterialType() == PlateMaterialType.ALUMINIUM && p.getElement().getPlateThickness() == PlateThickness.THICK).collect(Collectors.toList());
        if (!galvElem.isEmpty())
            placeElementsOnPlates(PlateMaterialType.ALUMINIUM, PlateThickness.THICK);
    }

    private void placeElementsOnPlates(PlateMaterialType materialType, PlateThickness thickness) {
        Plate galvPlateThick = new Plate();
        galvPlateThick.setMaterial(materialType);
        galvPlateThick.setThickness(thickness);
        for (Integer j = 0 ; j < drawLine.length; j++){
            drawLine[j]=0;
        }
        for (int i = 0; i < galvElem.size(); ){
            Integer minVal = getMinValue(drawLine);
            Integer firstIndex = java.util.Arrays.asList(drawLine).indexOf(minVal);
            Integer lastIndex = getMaxWidth(firstIndex, drawLine);
            Integer xMaxDim = lastIndex-firstIndex;
            Integer yMaxDim = maxLine - minVal;
            List<ElementWithQuantity> norm = galvElem.stream().collect(Collectors.toList());
            norm.sort(ElementWithQuantity.SORT_BY_X);
            List <ElementWithQuantity> rot = galvElem.stream().collect(Collectors.toList());
            rot.sort(ElementWithQuantity.SORT_BY_Y);

            int index = getFirstObject(norm,xMaxDim,yMaxDim);
            ElementWithQuantity normalObj = null;
            if (index > -1) {
                normalObj = norm.get(index);
            }
            index = getFirstObject(rot,yMaxDim,xMaxDim);
            ElementWithQuantity rotatedObj = null;
            if (index > -1) {
                rotatedObj = rot.get(index);
            }
            if(normalObj == null && rotatedObj == null ){
                int leftMax = firstIndex>0 ?  drawLine[firstIndex-1] : 0;
                int rightMax = lastIndex<1999 ?  drawLine[lastIndex + 1] : 0;
                if ( leftMax == rightMax && leftMax == 0) {
                    plates.add(galvPlateThick);
                    galvPlateThick = new Plate();
                    galvPlateThick.setMaterial(materialType);
                    galvPlateThick.setThickness(thickness);
                    for (Integer j = 0 ; j < drawLine.length; j++){
                        drawLine[j]=0;
                    }
                }else {
                    if ((leftMax <= rightMax && leftMax != 0) || rightMax == 0) {
                        for (int j = firstIndex; j <= lastIndex; j++) {
                            drawLine[j] = leftMax;
                        }
                    } else {
                        for (int j = firstIndex; j <= lastIndex; j++) {
                            drawLine[j] = rightMax;
                        }
                    }
                }

            }else {
                if (normalObj != null &&
                        (rotatedObj == null || normalObj.getElement().getDimensions().getX_dimension()
                        >= rotatedObj.getElement().getDimensions().getY_dimension())) {
                    ElementOnPlate element = new ElementOnPlate();
                    element.setElement(new Element(normalObj.getElement()));
                    element.setPos_x(Double.valueOf(firstIndex));
                    element.setPos_y(Double.valueOf(minVal));
                    double maxIndexPadding = firstIndex+normalObj.getElement().getDimensions().getX_dimension()+padding > 1999 ? 1999 : firstIndex+normalObj.getElement().getDimensions().getX_dimension()+padding;
                    for (int j = firstIndex; j <maxIndexPadding; j++ ){
                        double valDouble = drawLine[j] + normalObj.getElement().getDimensions().getY_dimension() + padding;
                        drawLine[j] = (int) valDouble;
                    }
                    if (normalObj.getQuantity() > 1 ){
                        normalObj.setQuantity( normalObj.getQuantity()-1);

                    }else{
                        norm.remove(normalObj);
                    }
                    galvPlateThick.elements.add(element);
                    galvElem = norm.stream().collect(Collectors.toList());

                }else {
                    if (rotatedObj != null &&
                            (normalObj == null || normalObj.getElement().getDimensions().getX_dimension()
                                    < rotatedObj.getElement().getDimensions().getY_dimension())) {
                            ElementOnPlate element = new ElementOnPlate();
                        element.setElement(new Element(rotatedObj.getElement()));
                        double setX = element.getElement().getDimensions().getY_dimension();
                        element.getElement().getDimensions().setY_dimension(element.getElement().getDimensions().getX_dimension());
                        element.getElement().getDimensions().setX_dimension(setX);
                        element.setPos_x(Double.valueOf(firstIndex));
                        element.setPos_y(Double.valueOf(minVal));
                        double maxIndexPadding = firstIndex+rotatedObj.getElement().getDimensions().getY_dimension()+padding > 1999 ? 1999 : firstIndex+rotatedObj.getElement().getDimensions().getY_dimension()+padding;
                        for (int j = firstIndex; j <maxIndexPadding; j++ ){
                            double valDouble = drawLine[j] + rotatedObj.getElement().getDimensions().getX_dimension() + padding;
                            drawLine[j] = (int) valDouble;
                        }

                        if (rotatedObj.getQuantity() > 1) {
                            rotatedObj.setQuantity(rotatedObj.getQuantity() - 1);

                        } else {
                            rot.remove(rotatedObj);
                        }
                        galvPlateThick.elements.add(element);
                        galvElem = rot.stream().collect(Collectors.toList());

                    }
                }

            }

        }
        plates.add(galvPlateThick);
    }

    public static int getMinValue(Integer[] numbers){
        int minValue = numbers[0];
        for(int i=1;i<numbers.length;i++){
            if(numbers[i] < minValue){
                minValue = numbers[i];
            }
        }
        return minValue;
    }

    public static int getFirstObject(List<ElementWithQuantity> norm, Integer xDim, Integer yDim){
        for(ElementWithQuantity o: norm){
            if(o.getElement().getDimensions().getX_dimension() <= xDim &&
                o.getElement().getDimensions().getY_dimension() <= yDim){
                return norm.indexOf(o);
            }
        }
        return -1;
    }

    public static int getMaxWidth(Integer firstIndex, Integer[] numbers){
        int value = numbers[firstIndex];
        for(int i=firstIndex; i<numbers.length;i++){
            if(numbers[i] !=  value){
                return i -1;
            }
        }
        return numbers.length-1;
    }
}
