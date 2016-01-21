package com.omnicrola.voxel.data;

import com.jme3.math.Vector3f;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by Eric on 1/20/2016.
 */
public class VectorXmlTypeAdapter extends XmlAdapter<SerialVector3f, Vector3f> {

    @Override
    public SerialVector3f marshal(Vector3f jmeVector) throws Exception {
        return new SerialVector3f(jmeVector);
    }

    @Override
    public Vector3f unmarshal(SerialVector3f serialVector) throws Exception {
        return serialVector.copyAsJmeVector();
    }
}
