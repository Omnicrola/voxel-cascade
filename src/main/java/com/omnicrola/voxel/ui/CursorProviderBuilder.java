package com.omnicrola.voxel.ui;

import com.jme3.asset.AssetManager;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.util.BufferUtils;
import com.omnicrola.voxel.ui.builders.UiConstants;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

/**
 * Created by Eric on 1/28/2016.
 */
public class CursorProviderBuilder {

    public Cursor2dProvider build(AssetManager assetManager) {
        HashMap<CursorToken, JmeCursor> cursors = new HashMap<>();
        cursors.put(CursorToken.DEFAULT, buildCursor(assetManager, UiConstants.Cursors.DEFAULT));
        cursors.put(CursorToken.ATTACK, buildCursor(assetManager, UiConstants.Cursors.ATTACK));
        cursors.put(CursorToken.MOVE, buildCursor(assetManager, UiConstants.Cursors.MOVE));
        cursors.put(CursorToken.BUILD, buildCursor(assetManager, UiConstants.Cursors.BUILD));
        return new Cursor2dProvider(cursors);
    }

    private JmeCursor buildCursor(AssetManager assetManager, String cursorFileName) {
        Texture texture = assetManager.loadTexture("Interface/cursors/" + cursorFileName);
        Image image = texture.getImage();
        ByteBuffer imgByteBuff = (ByteBuffer) image.getData(0).rewind();
        IntBuffer curIntBuff = BufferUtils.createIntBuffer(image.getHeight() * image.getWidth());

        while (imgByteBuff.hasRemaining()) {
            int rgba = imgByteBuff.getInt();
            int argb = ((rgba & 255) << 24) | (rgba >> 8);
            curIntBuff.put(argb);
        }

        JmeCursor cursor = new JmeCursor();
        cursor.setHeight(image.getHeight());
        cursor.setWidth(image.getWidth());
        cursor.setNumImages(1);
        cursor.setyHotSpot(image.getHeight() - 3);
        cursor.setxHotSpot(3);
        cursor.setImagesData((IntBuffer) curIntBuff.rewind());
        return cursor;
    }
}
