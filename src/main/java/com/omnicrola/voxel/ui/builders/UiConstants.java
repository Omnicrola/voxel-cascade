package com.omnicrola.voxel.ui.builders;

import de.lessvoid.nifty.tools.Color;

/**
 * Created by Eric on 1/25/2016.
 */
public class UiConstants {

    public static final String DEFAULT_FONT = "Interface/Fonts/Default.fnt";

    public static class Size {
        public static final String ONE_HUNDRED = "100%";
        public static final String ONE_THIRD = "33.3%";
    }

    public static class Colors {

        public static final Color TRANSPARENT = Color.NONE;
        public static final Color LIGHT_GRAY = new Color("#AAAAAAFF");
        public static final Color DARK_GRAY = new Color("#888888FF");
        public static final Color DARK_GREEN = new Color("#008800FF");
        public static final Color BLACK = Color.BLACK;
        public static final Color LIGHT_RED = new Color("#FFAAAA");
    }

    public static class Cursors {
        public static final String DEFAULT = "default.png";
        public static final String ATTACK = "attack.png";
        public static final String BUILD = "build.png";
        public static final String MOVE = "move.png";
    }
}
