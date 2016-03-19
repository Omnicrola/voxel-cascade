package com.omnicrola.voxel.ui.select;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.ui.builders.UiConstants;

/**
 * Created by Eric on 3/18/2016.
 */
public class HealthBarFactory {

    private final AssetManager assetManager;

    public HealthBarFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public HealthBar build() {
        Geometry backgroundBar = new Geometry("Quad", new Quad(0.9f, 0.3f));
        Geometry foregroundBar = new Geometry("Quad", new Quad(.86f, 0.04f));

        backgroundBar.setLocalTranslation(-0.45f, 0.42f, 0.5f);
        foregroundBar.setLocalTranslation(-0.43f, 0.44f, 0.51f);

        Material backgroundMaterial = new Material(assetManager, GameConstants.MATERIAL_UNSHADED);
        backgroundMaterial.setTexture("ColorMap", assetManager.loadTexture("Interface/healthbar.png"));
        backgroundBar.setMaterial(backgroundMaterial);

        Material foregroundMaterial = new Material(assetManager, GameConstants.MATERIAL_UNSHADED);
        foregroundMaterial.setColor("m_Color", ColorRGBA.Green);
        foregroundBar.setMaterial(foregroundMaterial);

        BitmapText bitmapText = buildText();

        HealthBar healthBar = new HealthBar("health", backgroundBar, foregroundBar, bitmapText);
        healthBar.addControl(new HealthBarControl());

        return healthBar;
    }

    private BitmapText buildText() {
        BitmapFont font = assetManager.loadFont(UiConstants.DEFAULT_FONT);
        BitmapText bitmapText = new BitmapText(font, false);
        bitmapText.setQueueBucket(RenderQueue.Bucket.Opaque);
        bitmapText.setSize(0.2f);
        bitmapText.setText("");
        bitmapText.setColor(ColorRGBA.White);
        bitmapText.setLocalTranslation(-0.43f, 0.73f, 0.52f);
        return bitmapText;
    }
}
