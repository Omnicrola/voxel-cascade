package com.omnicrola.voxel.ui;

import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.commands.ILocalCommand;
import com.omnicrola.voxel.data.ILevelChangeObserver;
import com.omnicrola.voxel.data.ILevelObserver;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.engine.GlobalGameState;
import com.omnicrola.voxel.engine.states.IStateTransition;
import com.omnicrola.voxel.engine.states.WorldManagerState;
import com.omnicrola.voxel.input.IUserSelectionObserver;
import com.omnicrola.voxel.input.WorldCursor;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ScreenBuilder;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Eric on 2/26/2016.
 */
public class UiAdapter implements ILevelChangeObserver {
    private final Nifty niftyGui;
    private final WorldManagerState worldManagerState;
    private final Map<GlobalGameState, IStateTransition> transitions;
    private AppStateManager stateManager;
    private final ArrayList<ILevelObserver> levelObservers;
    private final ArrayList<IUserSelectionObserver> selectionObservers;
    private LevelState currentLevel;

    public UiAdapter(Nifty niftyGui,
                     LevelManager levelManager,
                     WorldManagerState worldManagerState,
                     Map<GlobalGameState, IStateTransition> transitions,
                     AppStateManager stateManager) {
        this.niftyGui = niftyGui;
        this.worldManagerState = worldManagerState;
        this.transitions = transitions;
        this.stateManager = stateManager;

        levelManager.addObserver(this);
        this.levelObservers = new ArrayList<>();
        this.selectionObservers = new ArrayList<>();
    }

    public void addScreen(String screenName, ScreenBuilder screenBuilder) {
        niftyGui.addScreen(screenName, screenBuilder.build(niftyGui));
    }

    public void addUnitSelectionObserver(IUserSelectionObserver observer) {
        this.selectionObservers.add(observer);
        if (this.currentLevel != null) {
            this.currentLevel.getWorldCursor().addSelectionObserver(observer);
        }
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
        stateTranstion.run(this.niftyGui, this.stateManager);
    }

    public void sendCommand(ILocalCommand command) {
        this.worldManagerState.executeCommand(command);
    }

    @Override
    public void levelChanged(LevelState currentLevelState) {
        if (this.currentLevel != null) {
            WorldCursor worldCursor = this.currentLevel.getWorldCursor();
            this.levelObservers.forEach(o -> this.currentLevel.removeObserver(o));
            this.selectionObservers.forEach(o -> worldCursor.removeSelectionObserver(o));
        }
        this.currentLevel = currentLevelState;
        WorldCursor worldCursor = this.currentLevel.getWorldCursor();
        this.levelObservers.forEach(o -> this.currentLevel.addObserver(o));
        this.selectionObservers.forEach(o -> worldCursor.addSelectionObserver(o));
    }
}
