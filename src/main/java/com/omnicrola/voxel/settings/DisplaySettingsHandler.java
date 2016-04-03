package com.omnicrola.voxel.settings;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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

    public DisplaySettingsHandler() {
    }

    public DisplayMode getCurrentDisplayMode() {
        return Display.getDisplayMode();
    }

    public List<DisplayMode> getAvailableDisplayModes() {
        try {
            DisplayMode[] availableDisplayModes = Display.getAvailableDisplayModes();
            List<DisplayMode> collect = Arrays.stream(availableDisplayModes)
                    .sorted(DISPLAY_MODE_COMPARATOR)
                    .collect(Collectors.toList());
            return collect;
        } catch (LWJGLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

        return null;
    }
}
