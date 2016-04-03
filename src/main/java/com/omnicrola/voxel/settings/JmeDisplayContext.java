package com.omnicrola.voxel.settings;

import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;

/**
 * Created by Eric on 4/3/2016.
 */
public class JmeDisplayContext implements IDisplayContext {
    private JmeContext context;

    public JmeDisplayContext(JmeContext context) {
        this.context = context;
    }

    @Override
    public boolean isFullscreen() {
        return this.context.getSettings().isFullscreen();
    }

    @Override
    public DisplayModePackage getCurrentDisplayMode() {
        AppSettings settings = this.context.getSettings();
        DisplayResolution displayResolution = new DisplayResolution(settings.getWidth(), settings.getHeight());
        return new DisplayModePackage(displayResolution, settings.getSamples(), settings.isFullscreen());
    }

    @Override
    public void setSettings(DisplayModePackage selectedMode) {
        AppSettings settings = selectedMode.asJmeSettings();
        this.context.setSettings(settings);
        this.context.restart();
    }

    @Override
    public DisplayResolution getCurrentResolution() {
        AppSettings settings = this.context.getSettings();
        DisplayResolution displayResolution = new DisplayResolution(settings.getWidth(), settings.getHeight());
        return displayResolution;
    }
}
