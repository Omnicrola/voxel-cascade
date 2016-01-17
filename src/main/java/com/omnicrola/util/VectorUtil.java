package com.omnicrola.util;

import com.jme3.math.Vector3f;

/**
 * Created by omnic on 1/17/2016.
 */
public class VectorUtil {
    public static Vector3f scale(Vector3f vector3f, float scale) {
        vector3f.x *= scale;
        vector3f.y *= scale;
        vector3f.z *= scale;
        return vector3f;
    }
    public static Vector3f scale(Vector3f vector3f, Vector3f scale) {
        vector3f.x *= scale.x;
        vector3f.y *= scale.y;
        vector3f.z *= scale.z;
        return vector3f;
    }
}
