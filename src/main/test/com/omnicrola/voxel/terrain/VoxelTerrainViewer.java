package com.omnicrola.voxel.terrain;

import com.jme3.bullet.BulletAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.data.level.LevelDefinition;
import com.omnicrola.voxel.data.level.TerrainDefinition;
import com.omnicrola.voxel.data.units.EntityDefinitionXmlAssetLoader;
import com.omnicrola.voxel.debug.WireframeProcessor;
import com.omnicrola.voxel.engine.EngineShutdownHandler;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.jme.wrappers.impl.JmeGameContainer;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.terrain.build.PerlinNoiseGenerator;
import com.omnicrola.voxel.terrain.data.VoxelType;

import java.util.Arrays;

/**
 * Created by Eric on 2/2/2016.
 */
public class VoxelTerrainViewer extends VoxelGameEngine {

    public static final String RELOAD_TERRAIN = "ReloadTerrain";
    public static final String PRINT_GRAPH = "PrintGraph";
    public static final String TOGGLE_WIREFRAME = "toggle-wireframe";
    public static final String RELOAD_LEVEL = "reload-level";
    public static final String ADD_AMPLITUDE = "add-amplitude";
    public static final String SUBTRACT_AMPLITUDE = "subtract-amplitude";
    private JmeGameContainer jmeApplicationWrapper;
    private Node terrainNode;
    private WireframeProcessor wireframeProcessor;
    private PerlinNoiseGenerator perlinNoiseGenerator;

    private class DebugSceneGraphListener implements ActionListener {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed) {
                jmeApplicationWrapper.debugSceneGraph();
            }
        }
    }

    private class RebuildTerrainListener implements ActionListener {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed)
                reload();
            System.out.println("Rebuild terrain");
        }

    }

    private class ToggleWireframeListener implements ActionListener {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed) {
                wireframeProcessor.toggleEnabled();
            }
        }
    }

    private class ReloadLevelListener implements ActionListener {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed) {
                jmeApplicationWrapper.world().detatchTerrain(terrainNode);
                loadLevel();
                System.out.println("Reload level");
            }
        }
    }

    private class AdjustAmplitudeListener implements ActionListener {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {

            if (!isPressed) {
                float adjust = 0.1f;
                if (name.equals(ADD_AMPLITUDE)) {
//                perlinNoiseGenerator.setAmplitude(perlinNoiseGenerator.getAmplitude() + adjust);
                    perlinNoiseGenerator.setOctaves(perlinNoiseGenerator.getOctaves() + 1);
                    System.out.println("Add amplitude: " + perlinNoiseGenerator.getAmplitude());
                } else if (name.equals(SUBTRACT_AMPLITUDE)) {
//                perlinNoiseGenerator.setAmplitude(perlinNoiseGenerator.getAmplitude() - adjust);
                    perlinNoiseGenerator.setOctaves(perlinNoiseGenerator.getOctaves() - 1);
                    System.out.println("Sub amplitude: " + perlinNoiseGenerator.getAmplitude());
                }
                loadLevel();
            }
        }
    }

    public static void main(String[] args) {
        BulletAppState bulletAppState = new BulletAppState();
        VoxelTerrainViewer voxelChunkSimulation = new VoxelTerrainViewer(bulletAppState);
        voxelChunkSimulation.setShowSettings(false);

        AppSettings appSettings = new AppSettings(true);
        appSettings.setResolution(1024, 768);
        appSettings.setBitsPerPixel(32);
        appSettings.setFrequency(60);
        appSettings.put("Samples", 4);

        appSettings.setTitle("Voxel Terrain Simulation");

        voxelChunkSimulation.setSettings(appSettings);

        voxelChunkSimulation.start();
    }

    public VoxelTerrainViewer(BulletAppState bulletAppState) {
        super(bulletAppState, new EngineShutdownHandler());
    }

    @Override
    public void simpleInitApp() {
        this.stateManager.attach(this.bulletAppState);
        this.assetManager.registerLoader(EntityDefinitionXmlAssetLoader.class, GameConstants.UNIT_DEFINITION_FILE_EXTENSION);
        this.flyCam.setMoveSpeed(10);

        this.wireframeProcessor = new WireframeProcessor(getAssetManager());
        getViewPort().addProcessor(this.wireframeProcessor);

        jmeApplicationWrapper = new JmeGameContainer(this);
//        ChunkHandlerFactory chunkHandlerFactory = new ChunkHandlerFactory(jmeApplicationWrapper);
        perlinNoiseGenerator = new PerlinNoiseGenerator();
//        voxelTerrainGenerator = new OldVoxelTerrainGenerator(chunkHandlerFactory, perlinNoiseGenerator);
        loadLevel();

        RebuildTerrainListener rebuildTerrainListener = new RebuildTerrainListener();

        this.inputManager.addMapping(PRINT_GRAPH, new KeyTrigger(KeyInput.KEY_1));
        this.inputManager.addListener(new DebugSceneGraphListener(), PRINT_GRAPH);

        this.inputManager.addMapping(RELOAD_TERRAIN, new KeyTrigger(KeyInput.KEY_2));
        this.inputManager.addListener(rebuildTerrainListener, RELOAD_TERRAIN);

        this.inputManager.addMapping(TOGGLE_WIREFRAME, new KeyTrigger(KeyInput.KEY_3));
        this.inputManager.addListener(new ToggleWireframeListener(), TOGGLE_WIREFRAME);

        this.inputManager.addMapping(RELOAD_LEVEL, new KeyTrigger(KeyInput.KEY_4));
        this.inputManager.addListener(new ReloadLevelListener(), RELOAD_LEVEL);

        this.inputManager.addMapping(ADD_AMPLITUDE, new KeyTrigger(KeyInput.KEY_ADD));
        this.inputManager.addListener(new AdjustAmplitudeListener(), ADD_AMPLITUDE);
        this.inputManager.addMapping(SUBTRACT_AMPLITUDE, new KeyTrigger(KeyInput.KEY_MINUS));
        this.inputManager.addListener(new AdjustAmplitudeListener(), SUBTRACT_AMPLITUDE);

        DirectionalLight sun = new DirectionalLight();
        sun.setColor(new ColorRGBA(1f, 0.98f, 0.98f, 1f).mult(0.7f));
        sun.setDirection(new Vector3f(-0.3f, -0.8f, -0.5f).normalizeLocal());

        DirectionalLight sun2 = new DirectionalLight();
        sun2.setColor(new ColorRGBA(0.98f, 1f, 0.98f, 1f).mult(0.7f));
        sun2.setDirection(new Vector3f(0.5f, -0.4f, 0.3f).normalizeLocal());

        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(ColorRGBA.White.mult(0.4f));
        jmeApplicationWrapper.world().addLight(sun);
        jmeApplicationWrapper.world().addLight(sun2);
        jmeApplicationWrapper.world().addLight(ambientLight);

        jmeApplicationWrapper.gui().setCameraPosition(new Vector3f(0, 40, 40));
        this.getCamera().lookAt(new Vector3f(), Vector3f.UNIT_Y);
    }

    private void loadLevel() {
        if (terrainNode != null) {
            jmeApplicationWrapper.world().detatchTerrain(terrainNode);
        }
        LevelDefinition levelData = new LevelDefinition() {{
            this.terrain = new TerrainDefinition() {{
                this.terrainOffset = new Vec3i();
                this.width = 80;
                this.depth = 80;
                this.verticalScale = 15;
                this.octaves = 5;
            }};
        }};

        VoxelTypeLibrary voxelTypeLibrary = new VoxelTypeLibrary();
        Arrays.asList(VoxelType.values()).forEach(t -> voxelTypeLibrary.addType(t));
//        terrainNode = voxelTerrainGenerator.load(levelData, voxelTypeLibrary);
        jmeApplicationWrapper.world().attachTerrain(terrainNode);
    }

    private void reload() {
        System.out.println("rebuilding...");
        if (terrainNode != null) {
//            terrainNode.getControl(VoxelTerrainControl.class).forceRebuild();
        }
    }
}
