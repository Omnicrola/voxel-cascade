package com.omnicrola.voxel.main.init;

import com.jme3.bullet.BulletAppState;
import com.jme3.system.AppSettings;
import com.omnicrola.voxel.engine.EngineShutdownHandler;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.main.init.states.IStateInitializer;
import com.omnicrola.voxel.ui.builders.IGuiBuilder;

import java.util.ArrayList;

/**
 * Created by omnic on 1/15/2016.
 */
public class Bootstrapper {
    public static VoxelGameLauncher bootstrap() {

        ArrayList<IGuiBuilder> guiBuilders = new ArrayList<>();
        ArrayList<IStateInitializer> stateInitializers = new ArrayList<>();
        VoxelGameEngineInitializer initializer = new VoxelGameEngineInitializer(new InputMappingLoader(), new GuiInitializer(guiBuilders), stateInitializers);

        BulletAppState bulletAppState = new BulletAppState();
        VoxelGameEngine gameEngine = new VoxelGameEngine(initializer, bulletAppState, new EngineShutdownHandler());
        gameEngine.setShowSettings(false);

        AppSettings appSettings = new AppSettings(true);
        appSettings.setSettingsDialogImage("Textures/splash.jpg");
        appSettings.setResolution(1024, 600);
        appSettings.setBitsPerPixel(32);
        appSettings.setFrequency(60);
        appSettings.put("Samples", 4);

        appSettings.setTitle("Voxel Cascade");

        gameEngine.setSettings(appSettings);
        return new VoxelGameLauncher(gameEngine);
    }
}
