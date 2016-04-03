package com.omnicrola.voxel.settings;

/**
 * Created by Eric on 4/3/2016.
 */
public interface IDisplayContext {
    boolean isFullscreen();

    DisplayModePackage getCurrentDisplayMode();

    void setSettings(DisplayModePackage selectedMode);

    DisplayResolution getCurrentResolution();
}
