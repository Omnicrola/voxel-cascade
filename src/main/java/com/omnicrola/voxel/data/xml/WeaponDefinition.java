package com.omnicrola.voxel.data.xml;

import javax.xml.bind.annotation.*;

/**
 * Created by Eric on 1/18/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class WeaponDefinition {

    @XmlTransient
    public static final WeaponDefinition NULL = new WeaponDefinition();

    @XmlAttribute(name="global-id", required = true)
    protected int globalId;

    @XmlAttribute(name="projectile-id", required = true)
    protected int projectileId;

    @XmlElement(name="range",required = true)
    protected float range;

    @XmlElement(name="rounds-per-minute",required = true)
    protected float rateOfFire;


    public int getId() {
        return globalId;
    }

    public float getRange() {
        return range;
    }

    public float getRoundsPerMinute() {
        return rateOfFire;
    }

    public int getProjectileId() {
        return projectileId;
    }
}
