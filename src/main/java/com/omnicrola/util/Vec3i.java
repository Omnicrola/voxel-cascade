package com.omnicrola.util;

import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by omnic on 1/16/2016.
 */
@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
public class Vec3i implements Vec3iRead {

    @XmlAttribute(name = "x", required = true)
    private int x;
    @XmlAttribute(name = "y", required = true)
    private int y;
    @XmlAttribute(name = "z", required = true)
    private int z;

    public static Vec3i floor(Vector3f location) {
        return floor(location.x, location.y, location.z);
    }

    public static Vec3i floor(float x, float y, float z) {
        return new Vec3i(Math.round(x), Math.round(y), Math.round(z));
    }

//    public static Vec3i floor(Vector3f location) {
//        return new Vec3i((int) location.x, (int) location.y, (int) location.z);
//    }

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

    @Override
    public String toString() {
        return "Vec3i{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public Vec3i translate(int x, int y, int z) {
        return new Vec3i(this.x + x, this.y + y, this.z + z);
    }

    public float distance(Vector3f location) {
        float xd = this.x - location.x;
        float yd = this.y - location.y;
        float zd = this.z - location.z;
        return (float) Math.sqrt(xd * xd + yd * yd + zd * zd);
    }

    public float distance(Vec3i gridLocation) {
        float xd = this.x - gridLocation.x;
        float yd = this.y - gridLocation.y;
        float zd = this.z - gridLocation.z;
        return (float) Math.sqrt(xd * xd + yd * yd + zd * zd);
    }
}

