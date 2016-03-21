package com.omnicrola.voxel.main.init;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.omnicrola.voxel.commands.WorldCommandProcessor;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.engine.GlobalGameState;
import com.omnicrola.voxel.engine.states.IStateTransition;
import com.omnicrola.voxel.engine.states.transitions.TransitionActivePlay;
import com.omnicrola.voxel.engine.states.transitions.TransitionMainMenu;
import com.omnicrola.voxel.engine.states.transitions.TransitionMultiplayerJoin;
import com.omnicrola.voxel.engine.states.transitions.TransitionMultiplayerLoad;
import com.omnicrola.voxel.input.IWorldCursor;
import com.omnicrola.voxel.main.init.states.InitializationContainer;
import com.omnicrola.voxel.network.NetworkManager;
import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.builders.IGuiBuilder;
import com.omnicrola.voxel.ui.decorations.SpatialDecorator;
import com.omnicrola.voxel.ui.decorations.hp.HealthBarFactory;
import com.omnicrola.voxel.ui.decorations.selected.SelectionRingFactory;
import com.omnicrola.voxel.world.WorldManager;
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
        AssetManager assetManager = initializationContainer.getAssetManager();
        Nifty niftyGui = initializationContainer.getNiftyGui();
        LevelManager levelManager = initializationContainer.getLevelManager();
        AppStateManager stateManager = initializationContainer.getStateManager();
        WorldCommandProcessor worldCommandProcessor = initializationContainer.getWorldCommandProcessor();
        WorldManager worldManager = initializationContainer.getWorldManager();
        IWorldCursor worldCursor = worldManager.getWorldCursor();
        NetworkManager networkManager = initializationContainer.getNetworkManager();

        Map<GlobalGameState, IStateTransition> transitions = new HashMap<>();
        transitions.put(GlobalGameState.MULTIPLAYER_JOIN, new TransitionMultiplayerJoin());
        transitions.put(GlobalGameState.MULTIPLAYER_LOAD, new TransitionMultiplayerLoad());
        transitions.put(GlobalGameState.ACTIVE_PLAY, new TransitionActivePlay());
        transitions.put(GlobalGameState.MAIN_MENU, new TransitionMainMenu());

        HealthBarFactory healthBarFactory = new HealthBarFactory(assetManager);
        SelectionRingFactory selectionRingFactory = new SelectionRingFactory(assetManager);
        SpatialDecorator spatialDecorator = new SpatialDecorator(worldManager, healthBarFactory, selectionRingFactory);
        UiAdapter uiAdapter = new UiAdapter(
                niftyGui,
                levelManager,
                worldCursor,
                worldCommandProcessor,
                transitions,
                stateManager,
                spatialDecorator,
                networkManager);
        return uiAdapter;
    }

}
