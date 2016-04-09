package com.omnicrola.voxel.entities.control;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.data.TeamId;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.entities.commands.IDeathAction;
import com.omnicrola.voxel.entities.commands.NullDeathAction;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.ui.data.TeamStatistics;
import com.omnicrola.voxel.util.VoxelUtil;

/**
 * Created by omnic on 1/31/2016.
 */
public class DeathController extends AbstractControl {
    private IDeathAction deathAction;
    private EntityControlAdapter entityControlAdapter;

    public DeathController(EntityControlAdapter entityControlAdapter) {
        this.entityControlAdapter = entityControlAdapter;
        this.deathAction = NullDeathAction.NULL;
    }

    @Override
    protected void controlUpdate(float tpf) {
        float hitpoints = VoxelUtil.floatData(this.spatial, EntityDataKeys.HITPOINTS);
        if (hitpoints <= 0) {
            Node parent = this.spatial.getParent();
            if (parent != null) {
                this.deathAction.destruct(this.spatial);
                parent.detachChild(this.spatial);
                recordDeathStats();
            }
        }
    }

    private void recordDeathStats() {
        TeamId teamId = this.spatial.getUserData(EntityDataKeys.TEAM_DATA);
        LevelState currentLevel = this.entityControlAdapter.getCurrentLevel();
        TeamStatistics teamStatistics = currentLevel.getTeamStatistics(teamId);
        teamStatistics.increaseUnitsLost();
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
