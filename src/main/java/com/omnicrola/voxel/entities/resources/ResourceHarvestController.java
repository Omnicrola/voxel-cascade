package com.omnicrola.voxel.entities.resources;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.util.VoxelUtil;

/**
 * Created by Eric on 2/11/2016.
 */
public class ResourceHarvestController extends AbstractControl {
    private LevelState currentLevel;
    private IHarvestTarget harvestTarget;

    public ResourceHarvestController(LevelState currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void harvest(float tpf, IHarvestTarget harvestTarget) {
        float resources = harvestTarget.removeResources();
        TeamData teamData = this.spatial.getUserData(EntityDataKeys.TEAM_DATA);
        this.currentLevel.addResouces(teamData, resources);
    }

    public void setTarget(IHarvestTarget target) {
        this.harvestTarget = target;
    }

    private boolean isWithinHarvestRange() {
        float harvestRange = VoxelUtil.floatData(this.spatial, EntityDataKeys.HARVEST_RANGE);
        Vector3f worldLocation = this.spatial.getWorldTranslation();
        float distance = worldLocation.distance(this.harvestTarget.getLocation());
        return distance < harvestRange;
    }

    public boolean isHarvesting() {
        return this.harvestTarget != null;
    }

    public boolean isInRange() {
        Vector3f position = this.spatial.getWorldTranslation();
        Vector3f target = this.harvestTarget.getLocation();
        float range = VoxelUtil.floatData(this.spatial, EntityDataKeys.HARVEST_RANGE);
        float distance = position.distance(target);
        return distance < range;
    }

    public Vector3f getTargetLocation() {
        return this.harvestTarget.getLocation();
    }

    @Override
    protected void controlUpdate(float tpf) {

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
