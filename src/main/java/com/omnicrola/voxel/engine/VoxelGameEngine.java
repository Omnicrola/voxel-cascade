package com.omnicrola.voxel.engine;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.font.BitmapFont;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.omnicrola.voxel.data.units.EntityDefinitionXmlAssetLoader;
import com.omnicrola.voxel.jme.wrappers.impl.JmeApplicationWrapper;
import com.omnicrola.voxel.jme.wrappers.impl.JmeGuiWrapper;
import com.omnicrola.voxel.main.init.VoxelGameEngineInitializer;
import com.omnicrola.voxel.settings.GameConstants;
import de.lessvoid.nifty.Nifty;

/**
 * Created by omnic on 1/15/2016.
 */
public class VoxelGameEngine extends SimpleApplication {

    protected BulletAppState bulletAppState;
    private Nifty niftyGui;

    public VoxelGameEngine(BulletAppState bulletAppState) {
        this.bulletAppState = bulletAppState;
    }

    @Override
    public void simpleInitApp() {
        loadNiftyGui();
        this.stateManager.attach(this.bulletAppState);
        this.assetManager.registerLoader(EntityDefinitionXmlAssetLoader.class, GameConstants.UNIT_DEFINITION_FILE_EXTENSION);
        JmeApplicationWrapper jmeApplicationWrapper = new JmeApplicationWrapper(this);
        VoxelGameEngineInitializer.initializeGame(jmeApplicationWrapper, this.inputManager);
        this.bulletAppState.getPhysicsSpace().addCollisionListener(new MasterCollisionHandler());
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
}
