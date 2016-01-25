package com.omnicrola.voxel.engine.states;

import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.data.level.LevelGeneratorTool;
import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.builders.MainMenuUiBuilder;

/**
 * Created by omnic on 1/15/2016.
 */
public class MainMenuState extends VoxelGameState {

    private class StartGameListener implements ActionListener {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            gameContainer.disableState(MainMenuState.class);
            activePlayState.loadLevel(LevelGeneratorTool.BASIC_LEVEL_UUID);
            gameContainer.enableState(ActivePlayState.class);
        }
    }

    private ActivePlayState activePlayState;
    private IGameContainer gameContainer;

    public MainMenuState(ActivePlayState activePlayState) {
        super("Main Menu");
        this.activePlayState = activePlayState;
    }

    @Override
    protected void voxelInitialize(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
        StartGameListener startGameListener = new StartGameListener();
        addStateInput(GameInputAction.SELECT, startGameListener);
        MainMenuUiBuilder.build(gameContainer, this.activePlayState);
        setEnabled(false);
    }

    @Override
    protected void voxelEnable(IGameContainer gameContainer) {
        gameContainer.gui().changeScreens(UiScreen.MAIN_MENU);
    }

    @Override
    protected void voxelDisable(IGameContainer gameContainer) {
    }
}
