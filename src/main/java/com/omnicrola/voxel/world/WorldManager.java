package com.omnicrola.voxel.world;

import com.jme3.bounding.BoundingSphere;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.Projectile;
import com.omnicrola.voxel.entities.Structure;
import com.omnicrola.voxel.entities.Unit;
import com.omnicrola.voxel.input.IWorldCursor;
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
    private IWorldCursor worldCursor;
    private List<Unit> units;
    private List<Structure> structures;
    private List<Projectile> projectiles;

    public WorldManager(IWorldNode worldNode, IWorldCursor worldCursor) {
        this.worldNode = worldNode;
        this.worldCursor = worldCursor;
        this.units = new ArrayList<>();
        this.structures = new ArrayList<>();
        this.projectiles = new ArrayList<>();
    }

    public void addUnit(Unit unit) {
        this.units.add(unit);
        Spatial spatial = unit.getSpatial();
        this.worldNode.getUnitsNode().attachChild(spatial);
    }

    public void addStructure(Structure structure) {
        this.structures.add(structure);
        Spatial spatial = structure.getSpatial();
        this.worldNode.getUnitsNode().attachChild(spatial);
    }

    public void addProjectile(Projectile projectile) {
        this.projectiles.add(projectile);
        Spatial spatial = projectile.getSpatial();
        this.worldNode.getProjectilesNode().attachChild(spatial);
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
    }

    public List<IGameEntity> getAllUnits() {
        ArrayList<IGameEntity> gameEntities = new ArrayList<>();
        gameEntities.addAll(this.units);
        gameEntities.addAll(this.structures);
        return gameEntities;
    }

    public IWorldCursor getWorldCursor() {
        return worldCursor;
    }

    public float distanceToTerrainFloor(Vector3f location) {
        Vector3f surface = getTerrainSurfaceFrom(location);
        if (location.y > surface.y) {
            return surface.distance(location);
        } else {
            return surface.distance(location) * -1;
        }
    }

    private Vector3f getTerrainSurfaceFrom(Vector3f location) {
        Ray ray = new Ray(location, new Vector3f(0, -1, 0));
        CollisionResults results = new CollisionResults();
        this.worldNode.getTerrainNode().collideWith(ray, results);
        if (results.size() > 0) {
            CollisionResult closestCollision = results.getClosestCollision();
            return closestCollision.getContactPoint();
        }
        return new Vector3f(location).setY(0);
    }
}
