package com.omnicrola.voxel.entities.control.weapon;

import com.jme3.collision.CollisionResult;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.entities.control.old.DistanceSorter;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.util.VoxelUtil;
import com.omnicrola.voxel.world.WorldManager;

import java.util.Optional;

/**
 * Created by omnic on 1/23/2016.
 */
public class TargetingController extends AbstractControl {

    private static final float SCAN_INTERVAL = 1.0f;
    private static final float SCAN_RADIUS = 10.0f;

    private final DistanceSorter distanceSorter;
    private float lastTargetScan;
    private Spatial closestTarget;
    private WorldManager worldManager;

    public TargetingController(WorldManager worldManager) {
        this.worldManager = worldManager;
        this.distanceSorter = new DistanceSorter();
        this.lastTargetScan = 0f;
    }

    @Override
    protected void controlUpdate(float tpf) {
        this.lastTargetScan += tpf;
        if (this.lastTargetScan > SCAN_INTERVAL) {
            this.lastTargetScan = 0f;
            Vector3f position = this.spatial.getWorldTranslation();

            Optional<Geometry> closestTarget = this.worldManager.getUnitsInRange(position, SCAN_RADIUS)
                    .filter(c -> isTargetable(c))
                    .filter(c -> isEnemy(c))
                    .sorted(this.distanceSorter)
                    .map(c -> c.getGeometry())
                    .findFirst();
            if (closestTarget.isPresent()) {
                this.closestTarget = closestTarget.get();
            } else {
                this.closestTarget = null;
            }
        }
    }

    private boolean isTargetable(CollisionResult s) {
        return VoxelUtil.booleanData(s.getGeometry(), EntityDataKeys.IS_TARGETABLE);
    }

    private boolean isEnemy(CollisionResult collisionResult) {
        TeamData ourTeam = this.spatial.getUserData(EntityDataKeys.TEAM_DATA);
        TeamData otherTeam = collisionResult.getGeometry().getUserData(EntityDataKeys.TEAM_DATA);
        return !ourTeam.equals(otherTeam);
    }

    public Optional<Spatial> getClosestTarget() {
        return Optional.ofNullable(this.closestTarget);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
