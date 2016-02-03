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
import com.omnicrola.voxel.data.units.EntityDefinitionXmlAssetLoader;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.jme.wrappers.impl.JmeApplicationWrapper;
import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by Eric on 2/2/2016.
 */
public class VoxelChunkSimulation extends VoxelGameEngine {

    public static final String RELOAD_TERRAIN = "ReloadTerrain";
    public static final String PRINT_GRAPH = "PrintGraph";
    private JmeApplicationWrapper jmeApplicationWrapper;

    private class DebugSceneGraphListener implements ActionListener {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed) {
                jmeApplicationWrapper.debugSceneGraph();
            }
        }
    }

    private class ReloadTerrainListener implements ActionListener {

        private final VoxelTerrainGenerator voxelTerrainGenerator;
        private final LevelDefinition levelData;
        private Node terrainNode;

        public ReloadTerrainListener(VoxelTerrainGenerator voxelTerrainGenerator, LevelDefinition levelData) {
            this.voxelTerrainGenerator = voxelTerrainGenerator;
            this.levelData = levelData;
        }

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            reload();
        }

        private void reload() {
            if (this.terrainNode != null) {
                jmeApplicationWrapper.world().detatchTerrain(this.terrainNode);
            }
            this.terrainNode = this.voxelTerrainGenerator.load(levelData);
            jmeApplicationWrapper.world().attachTerrain(this.terrainNode);
        }
    }

    public static void main(String[] args) {
        BulletAppState bulletAppState = new BulletAppState();
        VoxelChunkSimulation voxelChunkSimulation = new VoxelChunkSimulation(bulletAppState);
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

    public VoxelChunkSimulation(BulletAppState bulletAppState) {
        super(bulletAppState);
    }

    @Override
    public void simpleInitApp() {
        this.stateManager.attach(this.bulletAppState);
        this.assetManager.registerLoader(EntityDefinitionXmlAssetLoader.class, GameConstants.UNIT_DEFINITION_FILE_EXTENSION);
        this.flyCam.setMoveSpeed(10);

        jmeApplicationWrapper = new JmeApplicationWrapper(this);
        VoxelTerrainGenerator voxelTerrainGenerator = new VoxelTerrainGenerator(jmeApplicationWrapper);
        LevelDefinition levelData = new LevelDefinition() {{
            this.terrainOffset = new Vec3i();
            this.terrainSize = new Vec3i(16, 16, 16);
        }};
        ReloadTerrainListener reloadTerrainListener = new ReloadTerrainListener(voxelTerrainGenerator, levelData);
        this.inputManager.addMapping(RELOAD_TERRAIN, new KeyTrigger(KeyInput.KEY_2));
        this.inputManager.addListener(reloadTerrainListener, RELOAD_TERRAIN);
        this.inputManager.addMapping(PRINT_GRAPH, new KeyTrigger(KeyInput.KEY_1));
        this.inputManager.addListener(new DebugSceneGraphListener(), PRINT_GRAPH);

        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White.mult(0.7f));
        sun.setDirection(new Vector3f(-0.5f, -0.5f, -0.5f).normalizeLocal());
        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(ColorRGBA.White.mult(0.3f));
        jmeApplicationWrapper.world().addLight(sun);
        jmeApplicationWrapper.world().addLight(ambientLight);

        reloadTerrainListener.reload();
    }
}
