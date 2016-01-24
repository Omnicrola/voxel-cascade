package com.omnicrola.voxel.data.units;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Eric on 1/18/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MovementDefinition {

    @XmlElement(name="max-velocity")
    protected float maxVelocity;

    @XmlElement(name="turnspeed")
    protected float turnspeed;

    @XmlElement(name="acceleration")
    protected float accelleration;

    @XmlElement(name = "personal-radius")
    protected float personalRadius;

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

    public float getPersonalRadius() {
        return personalRadius;
    }
}
