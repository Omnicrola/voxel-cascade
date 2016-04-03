package com.omnicrola.voxel.main.init;

import com.jme3.asset.AssetManager;
import com.omnicrola.voxel.commands.WorldCommandProcessor;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.input.IWorldCursor;
import com.omnicrola.voxel.main.init.states.InitializationContainer;
import com.omnicrola.voxel.network.NetworkManager;
import com.omnicrola.voxel.settings.DisplaySettingsHandler;
import com.omnicrola.voxel.settings.IDisplayContext;
import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.builders.IGuiBuilder;
import com.omnicrola.voxel.ui.decorations.SpatialDecorator;
import com.omnicrola.voxel.ui.decorations.hp.HealthBarFactory;
import com.omnicrola.voxel.ui.decorations.selected.SelectionRingFactory;
import com.omnicrola.voxel.world.WorldManager;
import de.lessvoid.nifty.Nifty;

import java.util.List;

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
        WorldCommandProcessor worldCommandProcessor = initializationContainer.getWorldCommandProcessor();
        WorldManager worldManager = initializationContainer.getWorldManager();
        IWorldCursor worldCursor = worldManager.getWorldCursor();
        NetworkManager networkManager = initializationContainer.getNetworkManager();

        HealthBarFactory healthBarFactory = new HealthBarFactory(assetManager);
        SelectionRingFactory selectionRingFactory = new SelectionRingFactory(assetManager);
        SpatialDecorator spatialDecorator = new SpatialDecorator(worldManager, healthBarFactory, selectionRingFactory);

        IDisplayContext appSettings = initializationContainer.getAppSettings();
        DisplaySettingsHandler displaySettingsHandler = new DisplaySettingsHandler(appSettings);
        UiAdapter uiAdapter = new UiAdapter(
                niftyGui,
                levelManager,
                worldCursor,
                worldCommandProcessor,
                spatialDecorator,
                networkManager,
                displaySettingsHandler);
        return uiAdapter;
    }

}
