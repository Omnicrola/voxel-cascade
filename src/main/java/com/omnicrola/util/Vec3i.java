package com.omnicrola.util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by omnic on 1/16/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Vec3i implements Vec3iRead {

    @XmlAttribute(name = "x", required = true)
    private int x;
    @XmlAttribute(name = "y", required = true)
    private int y;
    @XmlAttribute(name = "z", required = true)
    private int z;

    public Vec3i() {
    }

    public Vec3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }
}

