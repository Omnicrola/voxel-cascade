package com.omnicrola.voxel.main.init;

import com.jme3.bullet.BulletAppState;
import com.jme3.system.AppSettings;
import com.omnicrola.voxel.engine.VoxelGameEngine;

/**
 * Created by omnic on 1/15/2016.
 */
public class Bootstrapper {
    public static VoxelGameLauncher bootstrap() {
        BulletAppState bulletAppState = new BulletAppState();
        VoxelGameEngine gameEngine = new VoxelGameEngine(bulletAppState);
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
