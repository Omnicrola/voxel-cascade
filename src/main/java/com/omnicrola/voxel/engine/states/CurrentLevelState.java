package com.omnicrola.voxel.engine.states;

import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;

import java.util.ArrayList;

/**
 * Created by Eric on 2/6/2016.
 */
public class CurrentLevelState extends VoxelGameState implements ICurrentLevelProvider {
    private LevelState currentLevelState;
    private ArrayList<ILevelChangeObserver> observers;

    @Override
    protected void voxelInitialize(IGameContainer gameContainer) {
        this.observers = new ArrayList<>();
    }

    @Override
    protected void voxelEnable(IGameContainer gameContainer) {

    }

    @Override
    protected void voxelDisable(IGameContainer gameContainer) {

    }

    public void setCurrentLevel(LevelState levelState) {
        this.currentLevelState = levelState;
        notifyObservers();
    }

    private void notifyObservers() {
        this.observers.forEach(o -> o.levelChanged(this.currentLevelState));
    }

    @Override
    public LevelState getCurrentLevelState() {
        return this.currentLevelState;
    }

    @Override
    public void subscribe(ILevelChangeObserver levelChangeObserver) {
        this.observers.add(levelChangeObserver);
    }
}
