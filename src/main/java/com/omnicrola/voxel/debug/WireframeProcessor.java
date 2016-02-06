package com.omnicrola.voxel.debug;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.post.SceneProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.texture.FrameBuffer;

/**
 * Created by Eric on 2/3/2016.
 */
public class WireframeProcessor implements SceneProcessor {


    private final Material wireMaterial;
    private RenderManager renderManager;
    private boolean isEnabled;

    public WireframeProcessor(AssetManager assetManager) {
        this.wireMaterial = new Material(assetManager, "/Common/MatDefs/Misc/Unshaded.j3md");
        this.wireMaterial.setColor("Color", ColorRGBA.Blue);
        this.wireMaterial.getAdditionalRenderState().setWireframe(true);
    }

    @Override
    public void initialize(RenderManager rm, ViewPort vp) {
        this.renderManager = rm;
    }

    @Override
    public void reshape(ViewPort vp, int w, int h) {

    }

    @Override
    public boolean isInitialized() {
        return this.renderManager != null;
    }

    @Override
    public void preFrame(float tpf) {

    }

    @Override
    public void postQueue(RenderQueue rq) {
        if (this.isEnabled) {
            renderManager.setForcedMaterial(wireMaterial);
        }
    }

    @Override
    public void postFrame(FrameBuffer out) {
        renderManager.setForcedMaterial(null);
    }

    @Override
    public void cleanup() {
        renderManager.setForcedMaterial(null);
    }

    public void toggleEnabled() {
        this.isEnabled = !this.isEnabled;
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }
}
