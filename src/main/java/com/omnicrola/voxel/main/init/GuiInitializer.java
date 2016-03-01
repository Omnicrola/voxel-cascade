package com.omnicrola.voxel.main.init;

import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.commands.WorldCommandProcessor;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.engine.GlobalGameState;
import com.omnicrola.voxel.engine.states.IStateTransition;
import com.omnicrola.voxel.engine.states.transitions.TransitionActivePlay;
import com.omnicrola.voxel.engine.states.transitions.TransitionMainMenu;
import com.omnicrola.voxel.engine.states.transitions.TransitionMultiplayerLoad;
import com.omnicrola.voxel.input.IWorldCursor;
import com.omnicrola.voxel.main.init.states.InitializationContainer;
import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.builders.IGuiBuilder;
import de.lessvoid.nifty.Nifty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by omnic on 2/28/2016.
 */
public class GuiInitializer {
    private List<IGuiBuilder> guiBuilders;

    public GuiInitializer(List<IGuiBuilder> guiBuilders) {
        this.guiBuilders = guiBuilders;
    }

    public void createGui(InitializationContainer initializationContainer) {
        UiAdapter uiAdapter = buildUiAdapter(initializationContainer);
        this.guiBuilders.forEach(b -> b.build(uiAdapter));

    }

    private UiAdapter buildUiAdapter(InitializationContainer initializationContainer) {
        Nifty niftyGui = initializationContainer.getNiftyGui();
        LevelManager levelManager = initializationContainer.getLevelManager();
        AppStateManager stateManager = initializationContainer.getStateManager();
        WorldCommandProcessor worldCommandProcessor = initializationContainer.getWorldCommandProcessor();
        IWorldCursor worldCursor = initializationContainer.getWorldManager().getWorldCursor();

        Map<GlobalGameState, IStateTransition> transitions = new HashMap<>();
        transitions.put(GlobalGameState.MULTIPLAYER_LOAD, new TransitionMultiplayerLoad());
        transitions.put(GlobalGameState.ACTIVE_PLAY, new TransitionActivePlay());
        transitions.put(GlobalGameState.MAIN_MENU, new TransitionMainMenu());

        return new UiAdapter(niftyGui, levelManager, worldCursor, worldCommandProcessor, transitions, stateManager);
    }

}
