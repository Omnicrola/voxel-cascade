package com.omnicrola.voxel.ui;

import com.omnicrola.voxel.data.level.LevelGeneratorTool;
import com.omnicrola.voxel.engine.states.ActivePlayState;
import com.omnicrola.voxel.engine.states.MainMenuState;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.ui.builders.AbstractScreenController;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;

/**
 * Created by Eric on 1/24/2016.
 */
public class MainMenuScreenController extends AbstractScreenController {

    private IGameContainer gameContainer;
    private ActivePlayState activePlayState;

    public MainMenuScreenController(IGameContainer gameContainer, ActivePlayState activePlayState) {
        this.gameContainer = gameContainer;
        this.activePlayState = activePlayState;
    }

    @NiftyEventSubscriber(id = "BUTTON_START")
    @SubscriberLink(UiToken.BUTTON_START)
    public void start(String id, ButtonClickedEvent buttonClickedEvent) {
        this.gameContainer.disableState(MainMenuState.class);
        this.activePlayState.loadLevel(LevelGeneratorTool.BASIC_LEVEL_UUID);
        this.gameContainer.enableState(ActivePlayState.class);

    }


}
