package com.omnicrola.voxel.data.xml;

import javax.xml.bind.annotation.*;

/**
 * Created by Eric on 1/18/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class WeaponDefinition {

    @XmlAttribute(name="global-id", required = true)
    private int globalId;

    @XmlAttribute(name="projectile-id", required = true)
    private int projectileId;

    @XmlElement(name="range",required = true)
    private float range;

    @XmlElement(name="rounds-per-minute",required = true)
    private float rateOfFire;

    @XmlTransient
    public static final WeaponDefinition NULL = new WeaponDefinition();

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
