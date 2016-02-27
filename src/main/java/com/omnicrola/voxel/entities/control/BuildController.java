package com.omnicrola.voxel.entities.control;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.engine.states.ILevelManager;
import com.omnicrola.voxel.entities.commands.IConstructionPackage;
import com.omnicrola.voxel.fx.ParticleDurationControl;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.settings.EntityDataKeys;

/**
 * Created by omnic on 2/11/2016.
 */
public class BuildController extends AbstractControl {

    private IConstructionPackage constructionPackage;
    private boolean startedFx;
    private IGameContainer gameContainer;
    private ILevelManager levelProvider;
    private Spatial buildFx;

    public BuildController(IGameContainer gameContainer, ILevelManager levelProvider) {
        this.gameContainer = gameContainer;
        this.levelProvider = levelProvider;
        this.startedFx = false;
        clearConstructionPackage();
    }

    private void clearConstructionPackage() {
        this.constructionPackage = NullConstructionPackage.NO_OP;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (this.constructionPackage != NullConstructionPackage.NO_OP) {
            if (isInRange()) {
                if (this.constructionPackage.isFinished()) {
                    this.constructionPackage.completeConstruction();
                    clearConstructionPackage();
                    stopFx();
                } else {
                    build(tpf);
                }
            }
        }
    }

    private void startFx() {
        this.buildFx = this.gameContainer
                .world()
                .build()
                .particles()
                .cubicHarvest();
        this.gameContainer.world().attach(this.buildFx);
        this.buildFx.setLocalTranslation(getTargetLocation());
        this.startedFx = true;
    }

    private void stopFx() {
        if (this.buildFx != null) {
            ParticleDurationControl durationControl = this.buildFx.getControl(ParticleDurationControl.class);
            durationControl.resetDuration(0.1f);
        }
        this.startedFx = false;
    }

    private void build(float tpf) {
        if (!this.startedFx) {
            startFx();
        }
        LevelState currentLevel = this.levelProvider.getCurrentLevel();
        float resourcesUsed = this.constructionPackage.applyResourceTic(tpf);
        TeamData teamData = this.spatial.getUserData(EntityDataKeys.TEAM_DATA);
        currentLevel.removeResources(teamData, resourcesUsed);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }

    public void setPackage(IConstructionPackage constructionPackage) {
        this.constructionPackage = constructionPackage;
    }

    public Vector3f getTargetLocation() {
        return this.constructionPackage.getLocation();
    }

    public boolean isInRange() {
        if (this.constructionPackage != NullConstructionPackage.NO_OP) {
            Vector3f target = this.constructionPackage.getLocation();
            Vector3f location = this.spatial.getWorldTranslation();
            float distance = target.distance(location);
            return distance < 3f;
        }
        return false;
    }

    public boolean isFinished() {
        return this.constructionPackage.isFinished();
    }
}
