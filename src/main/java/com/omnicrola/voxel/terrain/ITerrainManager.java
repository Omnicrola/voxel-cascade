package com.omnicrola.voxel.terrain;

import com.omnicrola.voxel.data.level.TerrainDefinition;

/**
 * Created by Eric on 2/24/2016.
 */
public interface ITerrainManager {
    void globalReset();

    void load(TerrainDefinition terrain);
}
