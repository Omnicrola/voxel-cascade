package com.omnicrola.voxel.data.level;

import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.entities.Structure;
import com.omnicrola.voxel.entities.Unit;
import com.omnicrola.voxel.terrain.VoxelChunkHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 4/8/2016.
 */
public class LevelData {
    public final LevelDefinition levelDefinition;
    public final LevelSettings levelSettings;
    public final UnitDefinitionRepository unitDefinitionRepository;

    public List<Structure> structures = new ArrayList<>();
    public List<Unit> units = new ArrayList<>();
    public VoxelChunkHandler terrain;

    public LevelData(LevelDefinition levelDefinition, LevelSettings levelSettings, UnitDefinitionRepository unitDefinitionRepository) {
        this.levelDefinition = levelDefinition;
        this.levelSettings = levelSettings;
        this.unitDefinitionRepository = unitDefinitionRepository;
    }
}
