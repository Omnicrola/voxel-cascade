package com.omnicrola.voxel.data.units;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by omnic on 1/17/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectileDefinition {

    public static final ProjectileDefinition NONE = new ProjectileDefinition();

    @XmlAttribute(name = "global-id", required = true)
    protected int globalId;

    @XmlElement(name = "model", required = true)
    protected String model;

    @XmlElement(name = "texture", required = true)
    protected String texture;

    @XmlElement(name = "damage", required = true)
    protected float damage;

    @XmlElement(name = "speed", required = true)
    protected float muzzleVelocity;

    @XmlAttribute(name = "obeys-gravity")
    protected boolean obeysGravity = false;

    @XmlElement(name="physical-size", required = true)
    protected float size;

    public ProjectileDefinition() {
    }

    public int getId() {
        return this.globalId;
    }

    public String getModel() {
        return model;
    }

    public String getTexture() {
        return texture;
    }

    public float getDamage() {
        return this.damage;
    }

    public float getMuzzleVelocity() {
        return muzzleVelocity;
    }

    public boolean obeysGravity() {
        return obeysGravity;
    }

    public float getSize() {
        return size;
    }
}
