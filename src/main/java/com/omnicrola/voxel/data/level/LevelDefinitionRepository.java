package com.omnicrola.voxel.data.level;

import java.util.List;

/**
 * Created by Eric on 1/20/2016.
 */
public class LevelDefinitionRepository extends LevelDefinition {
    private List<LevelDefinition> levels;

    public LevelDefinitionRepository(List<LevelDefinition> levels) {
        this.levels = levels;
    }
}
