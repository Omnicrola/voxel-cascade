package com.omnicrola.voxel.ui;

import com.google.common.eventbus.Subscribe;
import com.omnicrola.voxel.commands.ICommandProcessor;
import com.omnicrola.voxel.commands.IWorldCommand;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.eventBus.events.CurrentLevelChangeEvent;
import com.omnicrola.voxel.network.INetworkManager;
import com.omnicrola.voxel.network.VoxelGameServer;
import com.omnicrola.voxel.settings.DisplaySettingsHandler;
import com.omnicrola.voxel.ui.decorations.ISpatialDecorator;
import com.omnicrola.voxel.ui.decorations.SpatialDecorator;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ScreenBuilder;

/**
 * Created by Eric on 2/26/2016.
 */
public class UiAdapter {

    private final Nifty niftyGui;
    private final ICommandProcessor commandProcessor;
    private SpatialDecorator spatialDecorator;
    private INetworkManager networkManager;
    private DisplaySettingsHandler displaySettingsHandler;
    private LevelState currentLevel;

    public UiAdapter(Nifty niftyGui,
                     ICommandProcessor commandProcessor,
                     SpatialDecorator spatialDecorator,
                     INetworkManager networkManager,
                     DisplaySettingsHandler displaySettingsHandler) {
        this.niftyGui = niftyGui;
        this.commandProcessor = commandProcessor;
        this.spatialDecorator = spatialDecorator;
        this.networkManager = networkManager;
        this.displaySettingsHandler = displaySettingsHandler;
    }

    public void addScreen(String screenName, ScreenBuilder screenBuilder) {
        niftyGui.addScreen(screenName, screenBuilder.build(niftyGui));
    }

    @Subscribe
    public void updateCurrentLevel(CurrentLevelChangeEvent event){
        this.currentLevel = event.getCurrentLevelState();
    }

    public LevelState getCurrentLevel() {
        return this.currentLevel;
    }

    public void sendCommand(IWorldCommand command) {
        this.commandProcessor.addCommand(command);
    }

    public ISpatialDecorator getSpatialDecorator() {
        return this.spatialDecorator;
    }

    public DisplaySettingsHandler getDisplaySettingsHandler() {
        return this.displaySettingsHandler;
    }

    public void gotoScreen(UiScreen uiScreen) {
        this.niftyGui.gotoScreen(uiScreen.toString());
    }
}
