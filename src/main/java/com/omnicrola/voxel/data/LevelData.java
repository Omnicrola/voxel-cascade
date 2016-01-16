package com.omnicrola.voxel.data;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.omnicrola.voxel.data.entities.EntityDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omnic on 1/16/2016.
 */
public class LevelData {

    private Vec3i terrainSize = new Vec3i(40, 5, 40);
    private Vec3i terrainOffset = new Vec3i(0, -5, 0);
    private List<EntityDefinition> entityDefinitions;

    public LevelData() {
        this.entityDefinitions = new ArrayList<>();
        this.entityDefinitions.add(new EntityDefinition(new Vector3f(0, -2, 0), ColorRGBA.Cyan));
        this.entityDefinitions.add(new EntityDefinition(new Vector3f(1, -2, 0), ColorRGBA.Cyan));
        this.entityDefinitions.add(new EntityDefinition(new Vector3f(2, -2, 0), ColorRGBA.Cyan));
    }

    public Vec3iRead getTerrainOffset() {
        return terrainOffset;
    }

    public Vec3iRead getTerrainSize() {
        return terrainSize;
    }

    public List<EntityDefinition> getEntityDefinitions() {
        return this.entityDefinitions;
    }
}
