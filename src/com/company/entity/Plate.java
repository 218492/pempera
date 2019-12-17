package com.company.entity;

import com.company.entity.enums.PlateMaterialType;
import com.company.entity.enums.PlateThickness;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Plate {
    public List<ElementOnPlate> elements = new LinkedList<>();
    PlateMaterialType material;
    PlateThickness thickness;
}
