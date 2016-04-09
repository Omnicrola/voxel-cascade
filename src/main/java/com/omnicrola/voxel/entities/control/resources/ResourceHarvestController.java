package com.omnicrola.voxel.entities.control.resources;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.data.TeamId;
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
                updateFx();
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
        this.harvestFx.setLocation(getTargetLocation());
        this.startedFx = true;
    }

    private void updateFx() {
        Vector3f location = this.harvestTarget.getLocation();
        this.harvestFx.setLocation(location);
    }

    private void stopFx() {
        this.harvestFx.resetDuration(0.1f);
        this.startedFx = false;
    }

    private void harvest(float tpf) {
        float resources = harvestTarget.removeResources(tpf);
        TeamId teamId = this.spatial.getUserData(EntityDataKeys.TEAM_DATA);
        LevelState currentLevel = this.controlAdapter.getCurrentLevel();
        currentLevel.addResouces(teamId, resources);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
