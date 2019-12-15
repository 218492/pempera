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
    List<OrderWithQuantity> galvElem, stainlessElem, aluElem;
    Integer[] drawLine = new Integer[2000]; //4000 x 2000
    Integer maxLine = 4000;
    Integer padding = 5;
    public static void  main(String[] args){
        List<OrderWithQuantity> input = new Vector<>();
        Order smallRect = new Order();
        smallRect.setDimensions(new Dimensions(100.0, 200.0));
        smallRect.setPlateThickness(PlateThickness.THICK);
        smallRect.setPlateMaterialType(PlateMaterialType.GALVANISED);
        smallRect.setPlateShape(PlateShape.RECTANGLE);
        OrderWithQuantity smallRects = new OrderWithQuantity();
        smallRects.setOrder(smallRect);
        smallRects.setQuantity(10);
        input.add(smallRects);
        Order smallRect2 = new Order();
        smallRect2.setDimensions(new Dimensions(300.0, 100.0));
        smallRect2.setPlateThickness(PlateThickness.THICK);
        smallRect2.setPlateMaterialType(PlateMaterialType.GALVANISED);
        smallRect2.setPlateShape(PlateShape.RECTANGLE);
        OrderWithQuantity smallRects2 = new OrderWithQuantity();
        smallRects2.setOrder(smallRect2);
        smallRects2.setQuantity(10);
        input.add(smallRects2);

        BestFit nowy = new BestFit(input);
        nowy.getPlates();
    }

    public BestFit(List<OrderWithQuantity> inputList){
        galvElem = inputList.stream().filter(p -> p.getOrder().getPlateMaterialType() == PlateMaterialType.GALVANISED).collect(Collectors.toList());
        Plate galvPlateThick = new Plate();
        galvPlateThick.setMaterial(PlateMaterialType.GALVANISED);
        galvPlateThick.setThickness(PlateThickness.THICK);
        for (Integer j = 0 ; j < drawLine.length; j++){
            drawLine[j]=0;
        }
        for (int i = 0; i < galvElem.size(); ){
            Integer minVal = getMinValue(drawLine);
            Integer firstIndex = java.util.Arrays.asList(drawLine).indexOf(minVal);
            Integer lastIndex = getMaxWidth(firstIndex, drawLine);
            Integer xMaxDim = lastIndex-firstIndex;
            Integer yMaxDim = maxLine - minVal;
            List <OrderWithQuantity> norm = galvElem.stream().collect(Collectors.toList());
            norm.sort(OrderWithQuantity.SORT_BY_X);
            List <OrderWithQuantity> rot = galvElem.stream().collect(Collectors.toList());
            rot.sort(OrderWithQuantity.SORT_BY_Y);

            int index = getFirstObject(norm,xMaxDim,yMaxDim);
            OrderWithQuantity normalObj = null;
            if (index > -1) {
                normalObj = norm.get(index);
            }
            index = getFirstObject(rot,yMaxDim,xMaxDim);
            OrderWithQuantity rotatedObj = null;
            if (index > -1) {
                rotatedObj = rot.get(index);
            }
            if(normalObj == null && rotatedObj == null ){
                int leftMax = firstIndex>0 ?  drawLine[firstIndex-1] : 0;
                int rightMax = lastIndex<1999 ?  drawLine[lastIndex + 1] : 0;
                if ( leftMax == rightMax && leftMax == 0) {
                    plates.add(galvPlateThick);
                    galvPlateThick = new Plate();
                    galvPlateThick.setMaterial(PlateMaterialType.GALVANISED);
                    galvPlateThick.setThickness(PlateThickness.THICK);
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
                        (rotatedObj == null || normalObj.getOrder().getDimensions().getDimension_X()
                        >= rotatedObj.getOrder().getDimensions().getDimension_Y())) {
                    ElementOnPlate element = new ElementOnPlate();
                    element.setElement(normalObj.getOrder());
                    element.setPos_x(Double.valueOf(firstIndex));
                    element.setPos_y(Double.valueOf(minVal));
                    double maxIndexPadding = firstIndex+normalObj.getOrder().getDimensions().getDimension_X()+padding > 1999 ? 1999 : firstIndex+normalObj.getOrder().getDimensions().getDimension_X()+padding;
                    for (int j = firstIndex; j <maxIndexPadding; j++ ){
                        double valDouble = drawLine[j] + normalObj.getOrder().getDimensions().getDimension_Y() + padding;
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
                            (normalObj == null || normalObj.getOrder().getDimensions().getDimension_X()
                                    < rotatedObj.getOrder().getDimensions().getDimension_Y())) {
                        ElementOnPlate element = new ElementOnPlate();
                        element.setElement(rotatedObj.getOrder());
                        element.setPos_x(Double.valueOf(firstIndex));
                        element.setPos_y(Double.valueOf(minVal));
                        double maxIndexPadding = firstIndex+rotatedObj.getOrder().getDimensions().getDimension_Y()+padding > 1999 ? 1999 : firstIndex+rotatedObj.getOrder().getDimensions().getDimension_Y()+padding;
                        for (int j = firstIndex; j <maxIndexPadding; j++ ){
                            double valDouble = drawLine[j] + rotatedObj.getOrder().getDimensions().getDimension_X() + padding;
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

    public static int getFirstObject(List<OrderWithQuantity> norm, Integer xDim, Integer yDim){
        for(OrderWithQuantity o: norm){
            if(o.getOrder().getDimensions().getDimension_X() <= xDim &&
                o.getOrder().getDimensions().getDimension_Y() <= yDim){
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
