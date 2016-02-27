package com.omnicrola.voxel.entities.control.old;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.entities.commands.IDeathAction;
import com.omnicrola.voxel.entities.commands.NullDeathAction;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.util.VoxelUtil;

/**
 * Created by omnic on 1/31/2016.
 */
public class DeathController extends AbstractControl {
    private IDeathAction deathAction;
    private IGameContainer gameContainer;

    public DeathController(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
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
//        LevelManager currentLevelState = this.gameContainer.getState(LevelManager.class);
//        LevelState currentLevel = currentLevelState.getCurrentLevel();
//        currentLevel.unitDestroyed(this.spatial);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
