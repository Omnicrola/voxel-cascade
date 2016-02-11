package com.omnicrola.voxel.jme.wrappers.impl;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.jme.wrappers.IGameGui;
import com.omnicrola.voxel.ui.UiScreen;

/**
 * Created by omnic on 1/15/2016.
 */
public class JmeGuiWrapper implements IGameGui {
    private static final float CAMERA_MOVE_SPEED = 10f;
    private VoxelGameEngine game;
    private GuiBuilder guiBuilder;

    public JmeGuiWrapper(VoxelGameEngine game) {
        this.game = game;
        this.guiBuilder = new GuiBuilder(this.game);
    }

    @Override
    public GuiBuilder build() {
        return this.guiBuilder;
    }

    @Override
    public void attach(Spatial node) {
        this.game.getGuiNode().attachChild(node);
    }

    @Override
    public void remove(Spatial node) {
        this.game.getGuiNode().detachChild(node);
    }

    @Override
    public void setCameraRotation(Quaternion rotation) {
        this.game.getCamera().setRotation(rotation);
    }

    @Override
    public void setCameraPosition(Vector3f position) {
        this.game.getCamera().setLocation(position);
    }

    @Override
    public void moveCamera(float amount, boolean sideways) {
        Camera camera = this.game.getCamera();
        Vector3f velocity = new Vector3f();
        Vector3f location = camera.getLocation().clone();
        if (sideways) {
//            camera.getLeft(velocity);
            location = camera.getLocation().add(amount * CAMERA_MOVE_SPEED, 0, 0);
        } else {
            location = camera.getLocation().add(0, 0, amount * CAMERA_MOVE_SPEED);
//            camera.getDirection(velocity);
        }

//        velocity.multLocal(amount * CAMERA_MOVE_SPEED);
//        location.addLocal(velocity);
        camera.setLocation(location);
    }

    @Override
    public void changeScreens(UiScreen screenName) {
        this.game.getNiftyGui().gotoScreen(screenName.toString());
    }
}
