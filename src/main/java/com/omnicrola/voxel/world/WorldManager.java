package com.omnicrola.voxel.world;

import com.jme3.bounding.BoundingSphere;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.Projectile;
import com.omnicrola.voxel.entities.Structure;
import com.omnicrola.voxel.entities.Unit;
import com.omnicrola.voxel.util.VoxelUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Eric on 2/22/2016.
 */
public class WorldManager {

    private IWorldNode worldNode;
    private List<Unit> units;
    private List<Structure> structures;
    private List<Projectile> projectiles;

    public WorldManager(IWorldNode worldNode) {
        this.worldNode = worldNode;
        this.units = new ArrayList<>();
        this.structures = new ArrayList<>();
        this.projectiles = new ArrayList<>();
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

    public Stream<CollisionResult> getUnitsInRange(Vector3f position, float scanRadius) {
        CollisionResults collisionResults = new CollisionResults();
        this.worldNode.getUnitsNode().collideWith(new BoundingSphere(scanRadius, position), collisionResults);
        return VoxelUtil.convertToStream(collisionResults);
    }

    public void addProjectile(Projectile projectile) {
        this.projectiles.add(projectile);
        this.worldNode.getProjectilesNode().attachChild(projectile.getSpatial());
    }

    public void removeTerrain(Spatial spatial) {
        // only remove physics
    }

    public void addTerrain(Node node) {
        // physics
    }
}
