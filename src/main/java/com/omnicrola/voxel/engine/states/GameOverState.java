package com.omnicrola.voxel.engine.states;

import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.input.listeners.QuitActiveGameListener;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.builders.GameOverUiBuilder;
import com.omnicrola.voxel.ui.data.TeamStatistics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omnic on 2/6/2016.
 */
public class GameOverState extends VoxelGameState implements IGameStatisticProvider {

    private IGameContainer gameContainer;

    public GameOverState() {
    }

    @Override
    protected void voxelInitialize(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
        GameOverUiBuilder.build(gameContainer, this);
        addStateInput(GameInputAction.SELECT, new QuitActiveGameListener(gameContainer));
    }

    @Override
    protected void voxelEnable(IGameContainer gameContainer) {
        gameContainer.gui().changeScreens(UiScreen.GAME_OVER);
    }

    @Override
    protected void voxelDisable(IGameContainer gameContainer) {

    }

    @Override
    public List<TeamStatistics> getTeamStatistics() {
        ActivePlayState activePlayState = this.gameContainer.getState(ActivePlayState.class);
        return new ArrayList<>();
    }
}
