package com.omnicrola.voxel.input.listeners;

import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.engine.states.ICurrentLevelProvider;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.input.actions.AttackCursorStrategy;
import com.omnicrola.voxel.ui.Cursor2dProvider;
import com.omnicrola.voxel.ui.CursorToken;

/**
 * Created by omnic on 1/24/2016.
 */
public class SetAttackCursorStrategyListener implements ActionListener {
    private ICurrentLevelProvider currentLevelProvider;
    private Cursor2dProvider cursor2dProvider;

    public SetAttackCursorStrategyListener(ICurrentLevelProvider currentLevelProvider, Cursor2dProvider cursor2dProvider) {
        this.currentLevelProvider = currentLevelProvider;
        this.cursor2dProvider = cursor2dProvider;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if(!isPressed){
            LevelState currentLevelState = this.currentLevelProvider.getCurrentLevelState();
            WorldCursor worldCursor = currentLevelState.getWorldCursor();
            JmeCursor attackCursor = this.cursor2dProvider.getCursor(CursorToken.ATTACK);
            worldCursor.setCursorStrategy(new AttackCursorStrategy(currentLevelState, attackCursor));
        }
    }
}
