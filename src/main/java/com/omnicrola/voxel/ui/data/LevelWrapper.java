package com.omnicrola.voxel.ui.data;

import com.omnicrola.voxel.data.level.LevelDefinition;

/**
 * Created by Eric on 4/10/2016.
 */
public class LevelWrapper {
    private LevelDefinition levelDefinition;

    public LevelWrapper(LevelDefinition levelDefinition) {
        this.levelDefinition = levelDefinition;
    }

    @Override
    public String toString() {
        return this.levelDefinition.getName();
    }

    public LevelDefinition getLevelDefinition() {
        return levelDefinition;
    }
}
