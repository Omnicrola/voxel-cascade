package com.omnicrola.voxel.entities.control.construction;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.entities.Effect;
import com.omnicrola.voxel.entities.commands.IConstructionPackage;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.jme.wrappers.impl.ParticleBuilder;
import com.omnicrola.voxel.settings.EntityDataKeys;

/**
 * Created by omnic on 2/11/2016.
 */
public class BuildController extends AbstractControl {

    private IConstructionPackage constructionPackage;
    private boolean startedFx;
    private Effect buildFx;
    private EntityControlAdapter entityControlAdapter;

    public BuildController(EntityControlAdapter entityControlAdapter) {
        this.entityControlAdapter = entityControlAdapter;
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
        ParticleBuilder effectsBuilder= this.entityControlAdapter.getParticleBuilder();
        this.buildFx = effectsBuilder.cubicHarvest();
        this.buildFx.setLocation(getTargetLocation());
        this.startedFx = true;
    }

    private void stopFx() {
        if (this.buildFx != null) {
            this.buildFx.resetDuration(0.1f);
        }
        this.startedFx = false;
    }

    private void build(float tpf) {
        if (!this.startedFx) {
            startFx();
        }

        this.buildFx.setLocation(this.constructionPackage.getLocation());
        LevelState currentLevel = this.entityControlAdapter.getCurrentLevel();
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
