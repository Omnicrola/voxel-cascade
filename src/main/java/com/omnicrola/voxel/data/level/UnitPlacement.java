package com.omnicrola.voxel.data.level;

import com.jme3.math.Vector3f;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Eric on 1/18/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class UnitPlacement {

    @XmlElement(name = "unit-id", required = true)
    protected int unitId;

    @XmlElement(name = "location", required = true)
    protected Vector3f location;

    public int getUnitId() {
        return unitId;
    }

    public Vector3f getLocation() {
        return location;
    }
}