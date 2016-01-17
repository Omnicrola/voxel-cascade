package com.omnicrola.util;

/**
 * Created by omnic on 1/16/2016.
 */
public class Vec3i implements Vec3iRead {

    private int x;
    private int y;
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

