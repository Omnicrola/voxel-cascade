package com.omnicrola.voxel.main.init;

import com.jme3.system.AppSettings;
import com.omnicrola.voxel.engine.VoxelGameEngine;

/**
 * Created by omnic on 1/15/2016.
 */
public class Bootstrapper {
    public static VoxelGameLauncher bootstrap() {
        VoxelGameEngine gameEngine = new VoxelGameEngine();

        AppSettings appSettings = new AppSettings(true);
        appSettings.setResolution(1024, 768);
        appSettings.setBitsPerPixel(32);
        appSettings.setFrequency(60);
        appSettings.setTitle("Voxel Cascade");

        gameEngine.setSettings(appSettings);
        return new VoxelGameLauncher(gameEngine);
    }
}