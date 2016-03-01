package com.omnicrola.voxel.main.init.states;

import com.jme3.app.state.AppState;
import com.jme3.input.InputManager;
import com.omnicrola.voxel.commands.WorldCommandProcessor;
import com.omnicrola.voxel.engine.states.CommandState;
import com.omnicrola.voxel.input.CursorCommandAdaptor;
import com.omnicrola.voxel.input.IWorldCursor;
import com.omnicrola.voxel.input.actions.SelectUnitsCursorStrategy;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.ui.Cursor2dProvider;
import com.omnicrola.voxel.ui.CursorProviderBuilder;
import com.omnicrola.voxel.world.WorldManager;
import com.omnicrola.voxel.world.build.WorldEntityBuilder;

/**
 * Created by Eric on 2/28/2016.
 */
public class CommandStateInitializer implements IStateInitializer {
    private CursorProviderBuilder cursorProviderBuilder;

    public CommandStateInitializer(CursorProviderBuilder cursorProviderBuilder) {
        this.cursorProviderBuilder = cursorProviderBuilder;
    }

    @Override
    public AppState buildState(InitializationContainer initializationContainer) {
        Cursor2dProvider cursor2dProvider = cursorProviderBuilder.build(initializationContainer.getAssetManager());
        InputManager inputManager = initializationContainer.getInputManager();
        WorldManager worldManager = initializationContainer.getWorldManager();
        IWorldCursor worldCursor = worldManager.getWorldCursor();
        WorldEntityBuilder worldEntityBuilder =initializationContainer.getWorldEntityBuilder();
        ITerrainManager terrainManager = initializationContainer.getTerrainManager();

        CursorCommandAdaptor cursorStrategyFactory = new CursorCommandAdaptor(
                inputManager,
                cursor2dProvider,
                worldEntityBuilder,
                worldManager,
                terrainManager);
        SelectUnitsCursorStrategy selectUnitsCursorStrategy = cursorStrategyFactory.setSelectStrategy();
        worldCursor.setDefaultCursorStrategy(selectUnitsCursorStrategy);
        worldCursor.clearCursorStrategy();


        WorldCommandProcessor worldCommandProcessor = initializationContainer.getWorldCommandProcessor();
        return new CommandState(worldCommandProcessor);
    }
}
