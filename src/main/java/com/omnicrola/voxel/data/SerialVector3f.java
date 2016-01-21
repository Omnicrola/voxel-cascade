package com.omnicrola.voxel.data;

import com.jme3.math.Vector3f;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by Eric on 1/20/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SerialVector3f {
    @XmlAttribute(name = "x", required = true)
    private final float x;
    @XmlAttribute(name = "y", required = true)
    private final float y;
    @XmlAttribute(name = "z", required = true)
    private final float z;

    public SerialVector3f(Vector3f jmeVector) {
        this.x = jmeVector.getX();
        this.y = jmeVector.getY();
        this.z = jmeVector.getZ();
    }

    public Vector3f copyAsJmeVector() {
        return new Vector3f(x, y, z);
    }
}
