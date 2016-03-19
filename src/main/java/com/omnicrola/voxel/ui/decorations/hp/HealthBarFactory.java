package com.omnicrola.voxel.ui.decorations.hp;

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

    public static final float BAR_HEIGHT = 0.04f;
    public static final float BAR_WIDTH = 0.9f;
    public static final float HALF_WIDTH = BAR_WIDTH / 2f;
    public static final float TEXT_SIZE = 0.2f;

    private final AssetManager assetManager;

    public HealthBarFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public HealthBar build() {
        Geometry backgroundBar = new Geometry("Quad", new Quad(BAR_WIDTH, BAR_HEIGHT));
        backgroundBar.setLocalTranslation(-HALF_WIDTH, 0, 0.5f);
        Material backgroundMaterial = new Material(assetManager, GameConstants.MATERIAL_UNSHADED);
        backgroundMaterial.setColor("Color", ColorRGBA.DarkGray);
        backgroundBar.setMaterial(backgroundMaterial);


        Geometry foregroundBar = new Geometry("Quad", new Quad(BAR_WIDTH, BAR_HEIGHT));
        foregroundBar.setLocalTranslation(-HALF_WIDTH, 0, 0.51f);
        Material foregroundMaterial = new Material(assetManager, GameConstants.MATERIAL_UNSHADED);
        foregroundMaterial.setColor("Color", ColorRGBA.Green);
        foregroundBar.setMaterial(foregroundMaterial);

        BitmapText bitmapText = buildText();

        HealthBar healthBar = new HealthBar(foregroundBar, bitmapText);
        healthBar.attachChild(backgroundBar);
        healthBar.attachChild(foregroundBar);
        healthBar.attachChild(bitmapText);
        healthBar.addControl(new HealthbarBillboardControl());

        return healthBar;
    }

    private BitmapText buildText() {
        BitmapFont font = assetManager.loadFont(UiConstants.DEFAULT_FONT);
        BitmapText bitmapText = new BitmapText(font, false);
        bitmapText.setQueueBucket(RenderQueue.Bucket.Opaque);
        bitmapText.setSize(TEXT_SIZE);
        bitmapText.setText("HP");
        bitmapText.setColor(ColorRGBA.White);
        bitmapText.setLocalTranslation(BAR_WIDTH / -2f, BAR_HEIGHT, 0.5f);
        return bitmapText;
    }
}
