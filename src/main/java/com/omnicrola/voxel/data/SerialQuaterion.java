package com.omnicrola.voxel.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by omnic on 1/31/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SerialQuaterion {

    @XmlAttribute
    public float x;
    @XmlAttribute
    public float y;
    @XmlAttribute
    public float z;
    @XmlAttribute
    public float w;

    public SerialQuaterion() {
    }

    public SerialQuaterion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
}
