package com.omnicrola.voxel.data.level;

import sun.plugin.dom.exception.InvalidStateException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Eric on 1/20/2016.
 */
public class LevelDefinitionRepository {
    private List<LevelDefinition> levels;

    public LevelDefinitionRepository(List<LevelDefinition> levels) {
        this.levels = levels;
    }

    public LevelDefinition getLevel(UUID levelGlobalId) {
        Optional<LevelDefinition> level = this.levels
                .stream()
                .filter(l -> l.getUuid().equals(levelGlobalId))
                .findFirst();

        if (level.isPresent()) {
            return level.get();
        }
        throw new InvalidStateException("Level with UUID " + levelGlobalId +
                " cannot be found in loaded definitions (" +
                this.levels.size() + " are loaded)");
    }
}
