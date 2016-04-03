package com.omnicrola.voxel.ui;

/**
 * Created by Eric on 1/24/2016.
 */
public class UiToken {
    public static final String SELECTION_PANEL = "";

    public static class MainMenu {

        public static final String BUTTON_QUIT_GAME = "button-quit-game";
        public static final String NEW_GAME = "button-new-game";
        public static final String BROWSE_GAMES = "button-browse-games";
        public static final String BUTTON_SETTINGS = "button-settings";
    }

    public static class Play {
        public static final String ACTION_1 = "unit-action-1";
        public static final String ACTION_2 = "unit-action-2";
        public static final String ACTION_3 = "unit-action-3";
        public static final String ACTION_4 = "unit-action-4";
        public static final String ACTION_5 = "unit-action-5";
        public static final String ACTION_6 = "unit-action-6";
        public static final String ACTION_7 = "unit-action-7";
        public static final String ACTION_8 = "unit-action-8";
        public static final String ACTION_9 = "unit-action-9";
        public static final String LABEL_RESOURCE_AMOUNT = "label-resource-amount";
    }

    public static class GameOver {
        public static final String TEAM_RESULTS_PANEL = "panel-team-results";
        public static final String ELAPSED_TIME = "label-elapsed-time";
        public static final String BUTTON_MAIN_MENU = "button-main-menu";
        public static final String TEAM_RESULTS_CONTAINER = "team-results-container";
        public static final String RESOURCE_AMOUNT = "label-resource-amount";
    }

    public static class Multiplayer {
        public static final String BUTTON_START = "button-start";
        public static final String BUTTON_JOIN = "button-join";
        public static final String BUTTON_CANCEL = "button-cancel";

        public static class Browse {
            public static final String MULTIPLAYER_SERVER_LIST = "combobox-server-list";
            public static final String LABEL_SERVER_NAME = "label-server-name";
            public static final String LABEL_SERVER_IP = "label-server-ip";
            public static final String LABEL_SERVER_PLAYERS = "label-server-players";
        }
    }

    public class Settings {
        public static final String BUTTON_DISPLAY = "button-display";
        public static final String SAVE = "button-save";
        public static final String CANCEL = "button-cancel";
        public static final String PANEL_AUDIO = "panel-audio";
        public static final String PANEL_GRAPHICS = "panel-graphics";
        public static final String RESOLUTION_DROPDOWN = "dropdown-resolution";
        public static final String CHECKBOX_SHADOWS = "checkbox-shadows";
        public static final String CHECKBOX_FULLSCREEN = "checkbox-fullscreen";
        public static final String DROPDOWN_ANTI_ALIAS = "dropdown-anti-alias";
        public static final String SLIDER_MASTER_VOLUME = "slider-master-volume";
    }
}
