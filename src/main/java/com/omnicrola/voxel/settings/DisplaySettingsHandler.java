package com.omnicrola.voxel.settings;

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
    private static final Comparator<DisplayMode> DISPLAY_MODE_COMPARATOR = new DisplayModeComparator();

    private static class DisplayModeComparator implements Comparator<DisplayMode> {
        @Override
        public int compare(DisplayMode dm1, DisplayMode dm2) {
            int area1 = dm1.getWidth() * dm1.getHeight();
            int area2 = dm2.getWidth() * dm2.getHeight();
            int areaCompare = Integer.compare(area1, area2);
            if (areaCompare != 0) {
                return areaCompare;
            }
            int bppCompare = Integer.compare(dm1.getBitsPerPixel(), dm2.getBitsPerPixel());
            if (bppCompare != 0) {
                return bppCompare;
            }
            return Integer.compare(dm1.getFrequency(), dm2.getFrequency());
        }
    }

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
    }

    public List<DisplayResolution> getAvailableResolutions() {
        try {
            DisplayMode[] availableDisplayModes = Display.getAvailableDisplayModes();
            return Arrays.stream(availableDisplayModes)
                    .filter(dm -> dm.getHeight() > 600)
                    .filter(dm -> dm.getWidth() > 800)
                    .map(dm -> new DisplayResolution(dm.getWidth(), dm.getHeight()))
                    .sorted()
                    .collect(Collectors.toList());
        } catch (LWJGLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

        return null;
    }
}
