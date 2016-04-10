package com.omnicrola.voxel.ui.controllers;

import com.google.common.eventbus.Subscribe;
import com.omnicrola.voxel.eventBus.VoxelEventBus;
import com.omnicrola.voxel.eventBus.events.LoadingStatusChangeEvent;
import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.builders.AbstractScreenController;
import com.omnicrola.voxel.ui.nifty.IUiElement;

/**
 * Created by Eric on 4/10/2016.
 */
public class LoadLevelScreenController extends AbstractScreenController {
    private UiAdapter uiAdapter;

    public LoadLevelScreenController(UiAdapter uiAdapter) {
        this.uiAdapter = uiAdapter;
    }

    @Subscribe
    public void updateLoadingPercentage(LoadingStatusChangeEvent event) {
        IUiElement statusLabel = ui().getElement(UiToken.Loading.STATUS_LABEL);
        IUiElement percentLabel = ui().getElement(UiToken.Loading.PERCENTAGE);
        statusLabel.setText(event.getStatus());
        percentLabel.setText(String.valueOf(event.getPercentComplete()) + "%");
    }

    @Override
    protected void screenOpen() {
        VoxelEventBus.INSTANCE().register(this);
    }

    @Override
    protected void screenClose() {
        VoxelEventBus.INSTANCE().unregister(this);
    }
}
