package com.omnicrola.voxel.terrain.highlight;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.terrain.data.VoxelData;

import java.util.List;

/**
 * Created by Eric on 3/4/2016.
 */
public interface ITerrainHighlightStrategy {
    List<VoxelData> findAllVoxelsInSelection(Vector3f startLocation, Vector3f endLocation);

}
