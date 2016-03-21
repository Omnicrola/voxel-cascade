package com.omnicrola.voxel.settings;

/**
 * Created by Eric on 1/18/2016.
 */
public class GameConstants {
    public static final String UNIT_DEFINITION_FILE_EXTENSION = "units";
    public static final String UNIT_DEFINITION_FILE = "Data/core-definitions.units";

    public static final String LEVEL_DEFINITION_FILE_EXTENSION = "level";
    public static final String LEVEL_DEFINITIONS_DIRECTORY = "Data/levels";

    public static final String MATERIAL_PARTICLE_SHADER = "Common/MatDefs/Misc/Particle.j3md";
    public static final String MATERIAL_UNSHADED = "Common/MatDefs/Misc/Unshaded.j3md";
    public static final String MATERIAL_SHADED = "Common/MatDefs/Light/Lighting.j3md";
    public static final String MATERIAL_GUI = "Common/MatDefs/Gui/Gui.j3md";

    public static final int CHUNK_SIZE = 16;
    public static final byte TERRAIN_TYPE_1 = 1;
    public static final float VOXEL_SIZE = 0.5f;
    public static final int CHUNK_SIZE_CUBED = CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE;
    public static final float MAXIMUM_VOXEL_HEIGHT = 256;
    public static final float MINIMUM_VOXEL_HEIGHT = 0;

    public static final String DIR_TEXTURES = "Textures/";
    public static final String DIR_MODELS = "Models/";

    public static final int SERVER_PORT = 5150;
    public static final String GAME_VERSION = "0.0.1";
    public static final String NODE_UNITS = "ALL_UNITS";
    public static final String NODE_WORLD_ROOT = "WORLD_ROOT";
    public static final String NODE_PROJECTILES = "PROJECTILES";
    public static final String NODE_FX = "FX";
    public static final String NODE_TERRAIN = "TERRAIN";

    public static final String SETTINGS_FILE = "voxel.settings";
    public static final String PROPERTY_LOG_LEVEL = "voxel-log-level";
    public static final int SERVER_BROADCAST_PORT = 5534;
}
