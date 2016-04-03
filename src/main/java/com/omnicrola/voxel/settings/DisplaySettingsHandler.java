package com.omnicrola.voxel.settings;

import com.omnicrola.voxel.main.settings.GameSettings;
import com.omnicrola.voxel.main.settings.GameSettingsRepository;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by omnic on 4/3/2016.
 */
public class DisplaySettingsHandler {

    private static final Logger LOGGER = Logger.getLogger(DisplaySettingsHandler.class.getName());

    private final Map<String, Integer> aliasingMap;
    private IDisplayContext displaySettings;

    public DisplaySettingsHandler(IDisplayContext displaySettings) {
        this.displaySettings = displaySettings;
        this.aliasingMap = createAliasingMap();
    }

    private Map<String, Integer> createAliasingMap() {
        HashMap<String, Integer> aliasingMap = new HashMap<>();
        aliasingMap.put("None", 0);
        aliasingMap.put("4x", 4);
        aliasingMap.put("8x", 8);
        return Collections.unmodifiableMap(aliasingMap);
    }

    public Map<String, Integer> getAliasingOptions() {
        return this.aliasingMap;
    }

    public int getAliasingForIndex(String name) {
        if (this.aliasingMap.containsKey(name)) {
            return this.aliasingMap.get(name);
        }
        return 0;
    }

    public boolean isCurrentlyFullscreen() {
        return this.displaySettings.isFullscreen();
    }

    public DisplayResolution getCurrentResolution() {
        return this.displaySettings.getCurrentResolution();
    }

    public void setDisplayMode(DisplayModePackage selectedMode) {
        this.displaySettings.setSettings(selectedMode);
        saveSettingsToFile(selectedMode);
    }

    private void saveSettingsToFile(DisplayModePackage selectedMode) {
        GameSettings settings = GameSettingsRepository.load();
        settings.displaySettings.antiAliasing = selectedMode.getAntiAliasing();
        settings.displaySettings.width = selectedMode.getResolution().getWidth();
        settings.displaySettings.height = selectedMode.getResolution().getHeight();
        settings.displaySettings.maxFps = selectedMode.getMaxFps();
        settings.displaySettings.fullscreen = selectedMode.isFullscreen();
        GameSettingsRepository.save(settings);
    }

    public List<DisplayResolution> getAvailableResolutions() {
        try {
            DisplayMode[] availableDisplayModes = Display.getAvailableDisplayModes();
            return Arrays.stream(availableDisplayModes)
                    .filter(dm -> dm.getHeight() >= 600)
                    .filter(dm -> dm.getWidth() >= 800)
                    .map(dm -> new DisplayResolution(dm.getWidth(), dm.getHeight()))
                    .sorted()
                    .collect(Collectors.toList());
        } catch (LWJGLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

        return new ArrayList<>();
    }
}
