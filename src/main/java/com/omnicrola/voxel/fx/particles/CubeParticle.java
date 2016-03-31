package com.omnicrola.voxel.fx.particles;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by Eric on 3/30/2016.
 */
public class CubeParticle extends Geometry {
    private final Vector3f velocity;
    private float lifeRemaining;

    public CubeParticle(AssetManager assetManager) {
        this.velocity = new Vector3f();
        this.lifeRemaining = 0f;
        this.mesh = new Box(0.125f, 0.125f, 0.125f);
        this.material = new Material(assetManager, GameConstants.MATERIAL_UNSHADED);
        setColor(ColorRGBA.White);
    }

    public void setColor(ColorRGBA color) {
        this.material.setColor("Color", color);
    }

    public boolean isInactive() {
        return this.parent == null;
    }

    public void setVelocity(Vector3f newVelocity) {
        this.velocity.set(newVelocity);
    }

    public void setLifeRemaining(float lifeRemaining) {
        this.lifeRemaining = lifeRemaining;
    }

    @Override
    public void updateLogicalState(float tpf) {
        super.updateLogicalState(tpf);
        this.setLocalTranslation(this.getLocalTranslation().addLocal(this.velocity));
        this.lifeRemaining -= tpf;
        if (this.lifeRemaining <= 0) {
            this.removeFromParent();
        }
    }
}
