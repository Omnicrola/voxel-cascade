package com.omnicrola.voxel.ui;

import com.omnicrola.voxel.data.ILevelObserver;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.input.IUserInteractionObserver;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ScreenBuilder;

/**
 * Created by Eric on 2/26/2016.
 */
public class UiAdapter {
    private VoxelGameEngine voxelGameEngine;

    public UiAdapter(VoxelGameEngine voxelGameEngine) {
        this.voxelGameEngine = voxelGameEngine;
    }

    public void addScreen(String screenName, ScreenBuilder screenBuilder) {
        Nifty niftyGui = this.voxelGameEngine.getNiftyGui();
        niftyGui.addScreen(screenName, screenBuilder.build(niftyGui));
    }

    public void addUnitSelectionObserver(IUserInteractionObserver observer) {

    }

    public void addCurrentLevelObserver(ILevelObserver levelObserver) {

    }
}
