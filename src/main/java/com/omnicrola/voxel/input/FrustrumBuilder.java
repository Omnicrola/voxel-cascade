package com.omnicrola.voxel.input;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by omnic on 1/23/2016.
 */
public class FrustrumBuilder {
    private static final float MIN_DISTANCE = 1.0f;

    public static Geometry build(AssetManager assetManager, Camera camera, ScreenRectangle screenRectangle, float frustrumDistance) {
        System.out.println("left " + camera.getFrustumLeft());
        System.out.println("right " + camera.getFrustumRight());
        System.out.println("top " + camera.getFrustumTop());
        System.out.println("bottom " + camera.getFrustumBottom());
        System.out.println("far " + camera.getFrustumFar());
        System.out.println("near " + camera.getFrustumNear());
        float nearProjectionZPos = 0.1f;
        Vector3f frontTopLeft = camera.getWorldCoordinates(screenRectangle.getTopLeft(), nearProjectionZPos);
        Vector3f frontTopRight = camera.getWorldCoordinates(screenRectangle.getTopRight(), nearProjectionZPos);
        Vector3f frontBottomRight = camera.getWorldCoordinates(screenRectangle.getBottomRight(), nearProjectionZPos);
        Vector3f frontBottomLeft = camera.getWorldCoordinates(screenRectangle.getBottomLeft(), nearProjectionZPos);

        float farProjectionZPos = 0.99f;
        Vector3f backTopLeft = camera.getWorldCoordinates(screenRectangle.getTopLeft(), farProjectionZPos);
        Vector3f backTopRight = camera.getWorldCoordinates(screenRectangle.getTopRight(), farProjectionZPos);
        Vector3f backBottomRight = camera.getWorldCoordinates(screenRectangle.getBottomRight(), farProjectionZPos);
        Vector3f backBottomLeft = camera.getWorldCoordinates(screenRectangle.getBottomLeft(), farProjectionZPos);

        Vector3f[] verticies = new Vector3f[8];
        verticies[0] = frontTopLeft;
        verticies[1] = frontTopRight;
        verticies[2] = frontBottomRight;
        verticies[3] = frontBottomLeft;
        verticies[4] = backTopLeft;
        verticies[5] = backTopRight;
        verticies[6] = backBottomRight;
        verticies[7] = backBottomLeft;

        Vector2f[] textureUvs = new Vector2f[1];
        textureUvs[0] = new Vector2f(0, 0);

        int[] indexes = {
                0, 1, 2,
                2, 3, 0,
                6, 5, 4,
                4, 7, 6,
                1, 5, 6,
                6, 2, 1,
                4, 0, 3,
                3, 7, 4,
                0, 4, 5,
                5, 1, 0,
                3, 2, 6,
                6, 7, 3
        };
        Mesh mesh = new Mesh();
        mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(verticies));
        mesh.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(textureUvs));
        mesh.setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createIntBuffer(indexes));
        mesh.updateBound();

        Geometry geometry = new Geometry("frustrum", mesh);
        Material material = new Material(assetManager, GameConstants.MATERIAL_SHADED);
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Ambient", ColorRGBA.Red);
        material.setColor("Diffuse", ColorRGBA.Red);
        geometry.setMaterial(material);

        return geometry;
    }
}
