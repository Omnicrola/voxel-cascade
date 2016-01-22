package com.omnicrola.voxel.data.level;

import com.omnicrola.voxel.input.WorldCursor;

/**
 * Created by Eric on 1/21/2016.
 */
public interface ILevelStateRead {
    String getLevelName();

    float getResources();

    float getTimeElapsed();

    WorldCursor getWorldCursor();
}
