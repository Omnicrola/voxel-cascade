package com.omnicrola.voxel.input.listeners;

import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.engine.states.ICurrentLevelProvider;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.input.actions.AttackCursorStrategy;

/**
 * Created by omnic on 1/24/2016.
 */
public class SetAttackCursorStrategyListener implements ActionListener {
    private ICurrentLevelProvider currentLevelProvider;

    public SetAttackCursorStrategyListener(ICurrentLevelProvider currentLevelProvider) {
        this.currentLevelProvider = currentLevelProvider;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if(!isPressed){
            LevelState currentLevelState = this.currentLevelProvider.getCurrentLevelState();
            WorldCursor worldCursor = currentLevelState.getWorldCursor();
            worldCursor.setCursorStrategy(new AttackCursorStrategy(currentLevelState));
        }
    }
}
