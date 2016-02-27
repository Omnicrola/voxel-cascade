package com.omnicrola.voxel.world;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.Structure;
import com.omnicrola.voxel.entities.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2/22/2016.
 */
public class WorldManager {

    private IWorldNode worldNode;
    private List<Unit> units;
    private List<Structure> structures;

    public WorldManager(IWorldNode worldNode) {
        this.worldNode = worldNode;
        this.units = new ArrayList<>();
        this.structures = new ArrayList<>();
    }

    public void addUnit(Unit unit) {
        this.units.add(unit);
        this.worldNode.getUnitsNode().attachChild(unit.getSpatial());
    }

    public void addStructure(Structure structure) {
        this.structures.add(structure);
        this.worldNode.getUnitsNode().attachChild(structure.getSpatial());
    }

    public void addEffect(IGameEntity gameEntity) {
        this.worldNode.getFxNode().attachChild(gameEntity.getSpatial());
    }

    public List<Unit> getAllUnits() {
        return new ArrayList<>(this.units);
    }

    public void removeSpatial(Spatial spatial) {

    }
}
