package com.omnicrola.voxel.data.xml;

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
    private int globalId;

    @XmlElement(name = "model", required = true)
    private String model;

    @XmlElement(name = "texture")
    private String texture;

    @XmlElement(name = "damage")
    private float damage;

    @XmlElement(name = "speed")
    private float muzzleVelocity;

    @XmlAttribute(name = "obeys-gravity")
    private boolean obeysGravity = false;

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
}
