package com.omnicrola.voxel.engine;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.font.BitmapFont;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.omnicrola.voxel.data.units.EntityDefinitionXmlAssetLoader;
import com.omnicrola.voxel.jme.wrappers.impl.JmeGameContainer;
import com.omnicrola.voxel.main.init.VoxelGameEngineInitializer;
import com.omnicrola.voxel.settings.GameConstants;
import de.lessvoid.nifty.Nifty;

/**
 * Created by omnic on 1/15/2016.
 */
public class VoxelGameEngine extends SimpleApplication {

    protected BulletAppState bulletAppState;
    private Nifty niftyGui;
    private LightManager lightManager;

    public VoxelGameEngine(BulletAppState bulletAppState) {
        this.bulletAppState = bulletAppState;
    }

    @Override
    public void simpleInitApp() {
        loadNiftyGui();
        this.stateManager.attach(this.bulletAppState);
        this.assetManager.registerLoader(EntityDefinitionXmlAssetLoader.class, GameConstants.UNIT_DEFINITION_FILE_EXTENSION);
        JmeGameContainer jmeApplicationWrapper = new JmeGameContainer(this);
        VoxelGameEngineInitializer.initializeGame(jmeApplicationWrapper, this.inputManager);
        this.bulletAppState.getPhysicsSpace().addCollisionListener(new MasterCollisionHandler());
        addLights();
    }

    private void addLights() {
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White.mult(0.80f));
        sun.setDirection(new Vector3f(-0.3f, -0.8f, -0.5f).normalizeLocal());

        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(ColorRGBA.White.mult(0.2f));

        this.rootNode.addLight(sun);
        this.rootNode.addLight(ambientLight);
        this.lightManager = new LightManager(sun, ambientLight);
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

    public BitmapFont getGuiFont() {
        return guiFont;
    }

    public PhysicsSpace getPhysicsSpace() {
        return this.bulletAppState.getPhysicsSpace();
    }

    public Nifty getNiftyGui() {
        return niftyGui;
    }

    public LightManager getLightManager() {
        return this.lightManager;
    }
}
