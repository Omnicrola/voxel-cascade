package com.omnicrola.voxel.engine.entities;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;

import java.io.IOException;

/**
 * Created by omnic on 1/16/2016.
 */
public class EntityData implements Savable{
    private boolean isTerrain;

    private EntityData(boolean isTerrain) {
        this.isTerrain = isTerrain;
    }

    public boolean isTerrain() {
        return this.isTerrain;
    }

    public static EntityData terrain() {
        return new EntityData(true);
    }

    public static EntityData entity() {
        return new EntityData(false);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {

    }

    @Override
    public void read(JmeImporter im) throws IOException {

    }
}
