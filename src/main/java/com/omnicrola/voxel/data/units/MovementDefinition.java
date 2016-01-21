package com.omnicrola.voxel.data.units;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by Eric on 1/18/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MovementDefinition {

    @XmlAttribute(name="max-velocity")
    protected float maxVelocity;

    @XmlAttribute(name="turnspeed")
    protected float turnspeed;

    @XmlAttribute(name="acceleration")
    protected float accelleration;

    public MovementDefinition() {
    }

    public float getAccelleration() {
        return accelleration;
    }

    public float getMaxVelocity() {
        return maxVelocity;
    }

    public float getTurnspeed() {
        return turnspeed;
    }
}
