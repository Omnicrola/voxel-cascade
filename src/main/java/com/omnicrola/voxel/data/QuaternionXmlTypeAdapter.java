package com.omnicrola.voxel.data;

import com.jme3.math.Quaternion;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by omnic on 1/31/2016.
 */
public class QuaternionXmlTypeAdapter extends XmlAdapter<SerialQuaterion, Quaternion> {
    @Override
    public Quaternion unmarshal(SerialQuaterion serial) throws Exception {
        return new Quaternion(serial.x, serial.y, serial.z, serial.w);
    }

    @Override
    public SerialQuaterion marshal(Quaternion quaternion) throws Exception {
        SerialQuaterion serialQuaterion = new SerialQuaterion();
        serialQuaterion.x = quaternion.getX();
        serialQuaterion.y = quaternion.getY();
        serialQuaterion.z = quaternion.getZ();
        serialQuaterion.w = quaternion.getW();
        return serialQuaterion;
    }
}
