package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.data.level.LevelGeneratorTool;
import com.omnicrola.voxel.engine.states.ActivePlayInputState;
import com.omnicrola.voxel.engine.states.CurrentLevelState;
import com.omnicrola.voxel.engine.states.MainMenuState;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.ui.SubscriberLink;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.builders.AbstractScreenController;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;

/**
 * Created by Eric on 1/24/2016.
 */
public class MainMenuScreenController extends AbstractScreenController {

    private IGameContainer gameContainer;

    public MainMenuScreenController(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
    }

    @NiftyEventSubscriber(id = "BUTTON_START")
    @SubscriberLink(UiToken.BUTTON_START)
    public void start(String id, ButtonClickedEvent buttonClickedEvent) {
        this.gameContainer.disableState(MainMenuState.class);
        this.gameContainer.getState(CurrentLevelState.class).loadLevel(LevelGeneratorTool.BASIC_LEVEL_UUID);
        this.gameContainer.enableState(CurrentLevelState.class);
        this.gameContainer.enableState(ActivePlayInputState.class);
    }

    @NiftyEventSubscriber(id = "BUTTON_MULTIPLAYER")
    @SubscriberLink(UiToken.BUTTON_MULTIPLAYER)
    public void launchMultiplayer(String id, ButtonClickedEvent buttonClickedEvent) {
        this.gameContainer.gui().changeScreens(UiScreen.MULTIPLAYER_LOAD);
        this.gameContainer.network().startLocalServer();
        this.gameContainer.network().connectTo("localhost");
        this.gameContainer.world().loadLevel(LevelGeneratorTool.BASIC_LEVEL_UUID);
    }
    @NiftyEventSubscriber(id = "BUTTON_QUIT_GAME")
    @SubscriberLink(UiToken.BUTTON_QUIT_GAME)
    public void quitGame(String id, ButtonClickedEvent buttonClickedEvent) {
        this.gameContainer.quitAndExit();
    }


}
