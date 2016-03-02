package com.omnicrola.voxel.entities.control.resources;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.entities.Effect;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.util.VoxelUtil;

/**
 * Created by Eric on 2/11/2016.
 */
public class ResourceHarvestController extends AbstractControl {
    private boolean startedFx;
    private IHarvestTarget harvestTarget;
    private Effect harvestFx;
    private EntityControlAdapter controlAdapter;

    public ResourceHarvestController(EntityControlAdapter controlAdapter) {
        this.controlAdapter = controlAdapter;
    }

    public void setTarget(IHarvestTarget target) {
        this.harvestTarget = target;
    }

    public boolean isHarvesting() {
        return this.harvestTarget != null;
    }

    public boolean isInRange() {
        if (this.harvestTarget != null) {
            Vector3f position = this.spatial.getWorldTranslation();
            Vector3f target = this.harvestTarget.getLocation();
            float range = VoxelUtil.floatData(this.spatial, EntityDataKeys.HARVEST_RANGE);
            float distance = position.distance(target);
            return distance < range;
        } else {
            return false;
        }
    }

    public Vector3f getTargetLocation() {
        return this.harvestTarget.getLocation();
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (isInRange()) {
            if (!startedFx) {
                startFx();
            }
            if (this.harvestTarget.hasResources()) {
                harvest(tpf);
            } else {
                this.harvestTarget.remove();
                stopFx();
                this.harvestTarget = null;
            }
        }
    }

    private void startFx() {
        this.harvestFx = this.controlAdapter.getParticleBuilder().cubicHarvest();
        this.controlAdapter.getWorldManager().addEffect(this.harvestFx);
        this.harvestFx.setLocation(getTargetLocation());
        this.startedFx = true;
    }

    private void stopFx() {
        this.harvestFx.resetDuration(0.1f);
        this.startedFx = false;
    }

    public void harvest(float tpf) {
        float resources = harvestTarget.removeResources(tpf);
        TeamData teamData = this.spatial.getUserData(EntityDataKeys.TEAM_DATA);
        LevelState currentLevel = this.controlAdapter.getCurrentLevel();
        currentLevel.addResouces(teamData, resources);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}