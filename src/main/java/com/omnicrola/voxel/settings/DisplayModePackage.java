package com.omnicrola.voxel.settings;

import com.jme3.system.AppSettings;

/**
 * Created by Eric on 4/3/2016.
 */
public class DisplayModePackage {
    private final int antiAliasing;
    private boolean fullscreen;
    private DisplayResolution displayResolution;

    public DisplayModePackage(DisplayResolution displayResolution, int antiAliasing, boolean fullscreen) {
        this.displayResolution = displayResolution;
        this.antiAliasing = antiAliasing;
        this.fullscreen = fullscreen;
    }

    public int getAntiAliasing() {
        return antiAliasing;
    }

    public DisplayResolution getResolution() {
        return this.displayResolution;
    }

    @Override
    public String toString() {
        return this.displayResolution.toString();
    }

    public AppSettings asJmeSettings() {
        AppSettings appSettings = new AppSettings(true);
        appSettings.setWidth(this.displayResolution.getWidth());
        appSettings.setHeight(this.displayResolution.getHeight());
        appSettings.setSamples(this.antiAliasing);
        appSettings.setFullscreen(this.fullscreen);
        return appSettings;
    }

    public int getMaxFps() {
        return 120;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }
}
