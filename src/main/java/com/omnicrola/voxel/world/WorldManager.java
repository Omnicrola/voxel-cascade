package com.omnicrola.voxel.world;

import com.jme3.bounding.BoundingSphere;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.Projectile;
import com.omnicrola.voxel.entities.Structure;
import com.omnicrola.voxel.entities.Unit;
import com.omnicrola.voxel.terrain.data.VoxelChunk;
import com.omnicrola.voxel.util.VoxelUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Eric on 2/22/2016.
 */
public class WorldManager {

    private IWorldNode worldNode;
    private PhysicsSpace physicsSpace;
    private List<Unit> units;
    private List<Structure> structures;
    private List<Projectile> projectiles;

    public WorldManager(IWorldNode worldNode, PhysicsSpace physicsSpace) {
        this.worldNode = worldNode;
        this.physicsSpace = physicsSpace;
        this.units = new ArrayList<>();
        this.structures = new ArrayList<>();
        this.projectiles = new ArrayList<>();
    }

    public void addUnit(Unit unit) {
        this.units.add(unit);
        Spatial spatial = unit.getSpatial();
        this.worldNode.getUnitsNode().attachChild(spatial);
        this.physicsSpace.add(spatial);
    }

    public void addStructure(Structure structure) {
        this.structures.add(structure);
        Spatial spatial = structure.getSpatial();
        this.worldNode.getUnitsNode().attachChild(spatial);
        this.physicsSpace.add(spatial);
    }

    public void addProjectile(Projectile projectile) {
        this.projectiles.add(projectile);
        Spatial spatial = projectile.getSpatial();
        this.worldNode.getProjectilesNode().attachChild(spatial);
        this.physicsSpace.add(spatial);
    }

    public void addEffect(IGameEntity gameEntity) {
        this.worldNode.getFxNode().attachChild(gameEntity.getSpatial());
    }

    public Stream<CollisionResult> getUnitsInRange(Vector3f position, float scanRadius) {
        CollisionResults collisionResults = new CollisionResults();
        this.worldNode.getUnitsNode().collideWith(new BoundingSphere(scanRadius, position), collisionResults);
        return VoxelUtil.convertToStream(collisionResults);
    }


    public void addTerrainChunk(VoxelChunk chunk) {
        this.worldNode.getTerrainNode().attachChild(chunk);
    }

    public void removeSpatial(Spatial spatial) {
        this.worldNode.getUnitsNode().detachChild(spatial);
        this.worldNode.getProjectilesNode().detachChild(spatial);
        this.worldNode.getFxNode().detachChild(spatial);
        this.worldNode.getTerrainNode().detachChild(spatial);
        this.units.remove(spatial);
        this.structures.remove(spatial);
        this.projectiles.remove(spatial);
        this.physicsSpace.remove(spatial);
    }
}
