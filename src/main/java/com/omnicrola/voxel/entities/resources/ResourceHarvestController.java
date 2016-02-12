package com.omnicrola.voxel.entities.resources;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.engine.states.CurrentLevelState;
import com.omnicrola.voxel.fx.ParticleDurationControl;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.util.VoxelUtil;

/**
 * Created by Eric on 2/11/2016.
 */
public class ResourceHarvestController extends AbstractControl {
    private boolean startedFx;
    private IHarvestTarget harvestTarget;
    private CurrentLevelState currentLevelState;
    private IGameContainer gameContainer;
    private Spatial harvestFx;

    public ResourceHarvestController(CurrentLevelState currentLevelState, IGameContainer gameContainer) {
        this.currentLevelState = currentLevelState;
        this.gameContainer = gameContainer;
        this.startedFx = false;
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
        this.harvestFx = this.gameContainer
                .world()
                .build()
                .particles()
                .cubicHarvest();
        this.gameContainer.world().attach(this.harvestFx);
        this.harvestFx.setLocalTranslation(getTargetLocation());
        this.startedFx = true;
    }

    private void stopFx() {
        this.harvestFx.getControl(ParticleDurationControl.class).resetDuration(0.1f);
        this.startedFx = false;
    }

    public void harvest(float tpf) {
        float resources = harvestTarget.removeResources(tpf);
        TeamData teamData = this.spatial.getUserData(EntityDataKeys.TEAM_DATA);
        this.currentLevelState.getCurrentLevel().addResouces(teamData, resources);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
