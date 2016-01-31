package com.omnicrola.util;

import com.jme3.math.Vector3f;

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

    @Override
    public Vector3f asVector3f() {
        return new Vector3f(this.x, this.y, this.z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vec3i vec3i = (Vec3i) o;

        if (x != vec3i.x) return false;
        if (y != vec3i.y) return false;
        return z == vec3i.z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }

    public Vec3i translate(int x, int y, int z) {
        return new Vec3i(this.x + x, this.y + y, this.z + z);
    }
}

