package com.omnicrola.voxel.ui;

import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.commands.ICommandProcessor;
import com.omnicrola.voxel.commands.IWorldCommand;
import com.omnicrola.voxel.data.ILevelChangeObserver;
import com.omnicrola.voxel.data.ILevelObserver;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.engine.GlobalGameState;
import com.omnicrola.voxel.engine.states.IStateTransition;
import com.omnicrola.voxel.input.IUserSelectionObserver;
import com.omnicrola.voxel.input.IWorldCursor;
import com.omnicrola.voxel.network.INetworkManager;
import com.omnicrola.voxel.network.INetworkObserver;
import com.omnicrola.voxel.network.VoxelGameServer;
import com.omnicrola.voxel.ui.decorations.ISpatialDecorator;
import com.omnicrola.voxel.ui.decorations.SpatialDecorator;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ScreenBuilder;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Eric on 2/26/2016.
 */
public class UiAdapter implements ILevelChangeObserver {

    private final ArrayList<ILevelObserver> levelObservers;

    private final Nifty niftyGui;
    private final IWorldCursor worldCursor;
    private final ICommandProcessor commandProcessor;
    private final Map<GlobalGameState, IStateTransition> transitions;
    private AppStateManager stateManager;
    private LevelState currentLevel;
    private SpatialDecorator spatialDecorator;
    private INetworkManager networkManager;

    public UiAdapter(Nifty niftyGui,
                     LevelManager levelManager,
                     IWorldCursor worldCursor,
                     ICommandProcessor commandProcessor,
                     Map<GlobalGameState, IStateTransition> transitions,
                     AppStateManager stateManager,
                     SpatialDecorator spatialDecorator,
                     INetworkManager networkManager) {
        this.niftyGui = niftyGui;
        this.worldCursor = worldCursor;
        this.commandProcessor = commandProcessor;
        this.transitions = transitions;
        this.stateManager = stateManager;
        this.spatialDecorator = spatialDecorator;
        this.networkManager = networkManager;

        levelManager.addObserver(this);
        this.levelObservers = new ArrayList<>();
    }

    public void addScreen(String screenName, ScreenBuilder screenBuilder) {
        niftyGui.addScreen(screenName, screenBuilder.build(niftyGui));
    }

    public void addUnitSelectionObserver(IUserSelectionObserver observer) {
        this.worldCursor.addSelectionObserver(observer);
    }

    public void addCurrentLevelObserver(ILevelObserver levelObserver) {
        this.levelObservers.add(levelObserver);
        if (this.currentLevel != null) {
            this.currentLevel.addObserver(levelObserver);
        }
    }

    public LevelState getCurrentLevel() {
        return this.currentLevel;
    }

    public void transitionTo(GlobalGameState globalGameState) {
        IStateTransition stateTranstion = this.transitions.get(globalGameState);
        stateTranstion.enter(this.niftyGui, this.stateManager);
    }

    public void sendCommand(IWorldCommand command) {
        this.commandProcessor.addCommand(command);
    }

    @Override
    public void levelChanged(LevelState currentLevelState) {
        if (this.currentLevel != null) {
            this.levelObservers.forEach(o -> this.currentLevel.removeObserver(o));
        }
        this.currentLevel = currentLevelState;
        this.levelObservers.forEach(o -> this.currentLevel.addObserver(o));
    }

    public ISpatialDecorator getSpatialDecorator() {
        return this.spatialDecorator;
    }

    public void addNetworkObserver(INetworkObserver networkObserver) {
        this.networkManager.addObserver(networkObserver);
    }

    public void joinServerLobby(VoxelGameServer multiplayerServer) {
        this.networkManager.stopListeningForServers();
        this.networkManager.connectTo(multiplayerServer.getAddress());
    }
}
