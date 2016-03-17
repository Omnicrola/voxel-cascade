package com.omnicrola.voxel.main.init;

import com.jme3.bullet.BulletAppState;
import com.jme3.system.AppSettings;
import com.omnicrola.voxel.data.GameXmlDataParser;
import com.omnicrola.voxel.data.read.FileReaderStrategyFactory;
import com.omnicrola.voxel.data.read.IFileReaderStrategy;
import com.omnicrola.voxel.engine.EngineShutdownHandler;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.main.init.states.*;
import com.omnicrola.voxel.main.settings.DisplaySettings;
import com.omnicrola.voxel.main.settings.GameSettings;
import com.omnicrola.voxel.main.settings.GameSettingsRepository;
import com.omnicrola.voxel.ui.CursorProviderBuilder;
import com.omnicrola.voxel.ui.builders.*;

import java.util.ArrayList;

/**
 * Created by omnic on 1/15/2016.
 */
public class Bootstrapper {
    public static VoxelGameLauncher bootstrap() {

        ArrayList<IGuiBuilder> guiBuilders = getiGuiBuilders();
        ArrayList<IStateInitializer> stateInitializers = getStateInitializers();
        IFileReaderStrategy fileReaderStrategy = FileReaderStrategyFactory.build();
        GameXmlDataParser gameXmlDataParser = new GameXmlDataParser(fileReaderStrategy);
        VoxelGameEngineInitializer initializer = new VoxelGameEngineInitializer(
                new InputMappingLoader(),
                new GuiInitializer(guiBuilders),
                stateInitializers,
                gameXmlDataParser);

        BulletAppState bulletAppState = new BulletAppState();
        VoxelGameEngine gameEngine = new VoxelGameEngine(initializer, bulletAppState, new EngineShutdownHandler());
        gameEngine.setShowSettings(false);

        GameSettings settings = GameSettingsRepository.load();
        DisplaySettings displaySettings = settings.displaySettings;

        AppSettings appSettings = new AppSettings(true);
        appSettings.setSettingsDialogImage("Textures/splash.jpg");
        appSettings.setResolution(displaySettings.width, displaySettings.height);
        appSettings.setBitsPerPixel(displaySettings.bpp);
        appSettings.setFrequency(displaySettings.hz);
        appSettings.setFrameRate(displaySettings.maxFps);
        appSettings.put("Samples", displaySettings.antiAliasing);

        appSettings.setTitle("Voxel Cascade");

        gameEngine.setSettings(appSettings);
        return new VoxelGameLauncher(gameEngine);
    }

    private static ArrayList<IGuiBuilder> getiGuiBuilders() {
        ArrayList<IGuiBuilder> guiBuilders = new ArrayList<>();
        guiBuilders.add(new ActivePlayUiBuilder());
        guiBuilders.add(new GameOverUiBuilder());
        guiBuilders.add(new MainMenuUiBuilder());
        guiBuilders.add(new MultiplayerUiBuilder());
        return guiBuilders;
    }

    private static ArrayList<IStateInitializer> getStateInitializers() {
        ArrayList<IStateInitializer> stateInitializers = new ArrayList<>();
        stateInitializers.add(new CommandStateInitializer(new CursorProviderBuilder()));
        stateInitializers.add(new ActivePlayInputStateInitializer());
        stateInitializers.add(new ClientNetworkStateInitializer());
        stateInitializers.add(new GameOverStateInitializer());
        stateInitializers.add(new LoadingStateInitializer());
        stateInitializers.add(new MainMenuStateInitializer());
        stateInitializers.add(new ShadowStateInitializer());
        stateInitializers.add(new DebugStateInitializer());
        return stateInitializers;
    }
}
