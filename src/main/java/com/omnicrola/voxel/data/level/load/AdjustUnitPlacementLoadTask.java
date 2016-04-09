package com.omnicrola.voxel.data.level.load;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.level.LevelData;
import com.omnicrola.voxel.terrain.data.VoxelData;

import java.util.Optional;

/**
 * Created by omnic on 4/9/2016.
 */
public class AdjustUnitPlacementLoadTask extends AbstractLoadTask {
    public AdjustUnitPlacementLoadTask(LevelData levelData) {
        super(levelData);
    }

    @Override
    protected void performLoading() {
        levelData.structures.forEach(s -> adjust(s.getSpatial()));
        levelData.units.forEach(u -> adjust(u.getSpatial()));
    }

    private void adjust(Spatial spatial) {
        Vector3f position = spatial.getWorldTranslation();
        Optional<VoxelData> lowestEmptyVoxel = levelData.terrain.findLowestEmptyVoxel(position);
        if (lowestEmptyVoxel.isPresent()) {
            Vector3f adjustedPosition = lowestEmptyVoxel.get().getGridLocation().asVector3f();
            spatial.setLocalTranslation(adjustedPosition);
        }
    }
}
