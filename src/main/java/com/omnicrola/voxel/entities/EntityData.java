package com.omnicrola.voxel.entities;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.omnicrola.voxel.entities.control.EntityAiController;
import com.omnicrola.voxel.entities.control.MotionGovernorControl;

import java.io.IOException;

/**
 * Created by omnic on 1/16/2016.
 */
public class EntityData implements Savable {

    public static EntityData terrain() {
        return new EntityData(true);
    }

    public static EntityData entity(MotionGovernorControl motionGovernor, EntityAiController entityAi) {
        return new EntityData(motionGovernor, entityAi);
    }

    private boolean isTerrain;
    private MotionGovernorControl motionGovernor;
    private EntityAiController entityAi;

    private EntityData(boolean isTerrain) {
        this.isTerrain = isTerrain;
    }

    private EntityData(MotionGovernorControl motionGovernor, EntityAiController entityAi) {
        this.motionGovernor = motionGovernor;
        this.entityAi = entityAi;
        this.isTerrain = false;
    }

    public boolean isTerrain() {
        return this.isTerrain;
    }


    @Override
    public void write(JmeExporter ex) throws IOException {

    }

    @Override
    public void read(JmeImporter im) throws IOException {

    }

    public MotionGovernorControl getMotionGovernor() {
        return motionGovernor;
    }

    public EntityAiController getEntityAi() {
        return entityAi;
    }
}
