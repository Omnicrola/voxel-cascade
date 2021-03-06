package com.omnicrola.voxel.engine;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.font.BitmapFont;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.omnicrola.voxel.data.units.EntityDefinitionXmlAssetLoader;
import com.omnicrola.voxel.main.init.VoxelGameEngineInitializer;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.world.IWorldNode;
import com.omnicrola.voxel.world.WorldRootNode;
import de.lessvoid.nifty.Nifty;

/**
 * Created by omnic on 1/15/2016.
 */
public class VoxelGameEngine extends SimpleApplication implements IActionQueue {

    private VoxelGameEngineInitializer gameEngineInitializer;
//    protected BulletAppState bulletAppState;
    private EngineShutdownHandler shutdownHandler;
    private Nifty niftyGui;
    private LightManager lightManager;
    private VoxelTickProvider ticProvider;
    private WorldRootNode worldRootNode;
    private boolean isInitialized;

    public VoxelGameEngine(VoxelGameEngineInitializer gameEngineInitializer,
                           BulletAppState bulletAppState,
                           EngineShutdownHandler shutdownHandler) {
        this.gameEngineInitializer = gameEngineInitializer;
//        this.bulletAppState = bulletAppState;
        this.shutdownHandler = shutdownHandler;
    }

    @Override
    public void simpleInitApp() {
        this.assetManager.registerLoader(EntityDefinitionXmlAssetLoader.class, GameConstants.UNIT_DEFINITION_FILE_EXTENSION);
        setPauseOnLostFocus(false);

        loadNiftyGui();
        addLights();
        this.worldRootNode = new WorldRootNode();
        this.rootNode.attachChild(this.worldRootNode);

        this.ticProvider = new VoxelTickProvider();
//        this.stateManager.attach(this.bulletAppState);
        this.gameEngineInitializer.initialize(this);
//        this.bulletAppState.getPhysicsSpace().addCollisionListener(new MasterCollisionHandler());
        this.isInitialized = true;
    }

    private void addLights() {
        DirectionalLight sun = new DirectionalLight();

        sun.setColor(new ColorRGBA(1f, 1f, 0.9843f, 1f).mult(0.9f));
        sun.setDirection(new Vector3f(-0.3f, -0.8f, -0.5f).normalizeLocal());

        DirectionalLight rimLight = new DirectionalLight();
        rimLight.setColor(new ColorRGBA(0.2509f, 0.6117f, 1f, 1f).mult(0.3f));

        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(ColorRGBA.White.mult(0.3f));

        this.rootNode.addLight(sun);
        this.rootNode.addLight(rimLight);
        this.rootNode.addLight(ambientLight);
        this.lightManager = new LightManager(sun, rimLight, ambientLight);
    }


    private void loadNiftyGui() {
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(
                this.assetManager, this.inputManager, this.audioRenderer, this.guiViewPort);
        this.niftyGui = niftyDisplay.getNifty();
        this.niftyGui.setIgnoreKeyboardEvents(true);
        this.guiViewPort.addProcessor(niftyDisplay);
        this.flyCam.setDragToRotate(true);

        niftyGui.loadStyleFile("nifty-default-styles.xml");
        niftyGui.loadControlFile("nifty-default-controls.xml");
    }


    @Override
    public void simpleRender(RenderManager rm) {

    }

    @Override
    public void simpleUpdate(float tpf) {

    }

    @Override
    public void stop() {
        super.stop();
        this.shutdownHandler.shutdownAndExit(this);
    }

    @Override
    public Node getRootNode() {
        return this.worldRootNode;
    }

    public BitmapFont getGuiFont() {
        return guiFont;
    }

    public Nifty getNiftyGui() {
        return niftyGui;
    }

    public LightManager getLightManager() {
        return this.lightManager;
    }

    public ITickProvider getTicProvider() {
        return this.ticProvider;
    }

    public IWorldNode getWorldNode() {
        return this.worldRootNode;
    }

    public boolean isInitialized() {
        return isInitialized;
    }
}
