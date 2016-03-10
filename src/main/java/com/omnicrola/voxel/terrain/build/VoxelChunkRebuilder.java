package com.omnicrola.voxel.terrain.build;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.omnicrola.voxel.entities.control.collision.CollisionController;
import com.omnicrola.voxel.entities.control.collision.TerrainCollisionHandler;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.terrain.data.VoxelChunk;
import com.omnicrola.voxel.terrain.data.VoxelFace;
import com.omnicrola.voxel.world.WorldManager;
import jme3tools.optimize.GeometryBatchFactory;

public class VoxelChunkRebuilder {

    private static final int CHUNK_WIDTH = GameConstants.CHUNK_SIZE;
    private static final int CHUNK_HEIGHT = GameConstants.CHUNK_SIZE;
    public static final float VOXEL_SIZE = 1.0f;

    private TerrainQuadFactory quadFactory;
    private WorldManager worldManager;

    public VoxelChunkRebuilder(TerrainQuadFactory quadFactory, WorldManager worldManager) {
        this.quadFactory = quadFactory;
        this.worldManager = worldManager;
    }

    public void rebuild(VoxelChunk chunk) {
        chunk.detachAllChildren();
        Node node = new Node();
        sweepAllThreeAxes(chunk, node, true);
        sweepAllThreeAxes(chunk, node, false);

        Node batchNode = (Node) GeometryBatchFactory.optimize(node);
        batchNode.getChildren().forEach(c -> {
            c.setUserData(EntityDataKeys.IS_TERRAIN, true);
            c.setUserData(EntityDataKeys.IS_COLLIDABLE, true);
            c.addControl(new CollisionController(new TerrainCollisionHandler(c, this.worldManager)));
        });
        chunk.attachChild(batchNode);
        chunk.clearRebuildFlag();
    }

    private void sweepAllThreeAxes(VoxelChunk chunk, Node parentNode, boolean backFace) {
        int u, v, n, side = 0;

        final int[] x = new int[]{0, 0, 0};
        final int[] q = new int[]{0, 0, 0};
        final int[] du = new int[]{0, 0, 0};
        final int[] dv = new int[]{0, 0, 0};

        final VoxelFace[] mask = new VoxelFace[GameConstants.CHUNK_SIZE * GameConstants.CHUNK_SIZE];
        VoxelFace voxelFace, voxelFace1;

        for (int d = 0; d < 3; d++) {

            u = (d + 1) % 3;
            v = (d + 2) % 3;

            x[0] = 0;
            x[1] = 0;
            x[2] = 0;

            q[0] = 0;
            q[1] = 0;
            q[2] = 0;
            q[d] = 1;

            side = getSide(backFace, side, d);
            for (x[d] = -1; x[d] < CHUNK_WIDTH; ) {

                n = 0;
                for (x[v] = 0; x[v] < CHUNK_HEIGHT; x[v]++) {

                    for (x[u] = 0; x[u] < CHUNK_WIDTH; x[u]++) {

                        voxelFace = (x[d] >= 0) ? chunk.getVoxelFace(x[0], x[1], x[2], side) : null;
                        voxelFace1 = (x[d] < CHUNK_WIDTH - 1) ? chunk.getVoxelFace(x[0] + q[0], x[1] + q[1], x[2] + q[2], side) : null;

                        if (voxelFace != null && voxelFace1 != null && voxelFace.equals(voxelFace1)) {
                            mask[n] = null;
                        } else {
                            mask[n] = backFace ? voxelFace1 : voxelFace;
                        }
                        n++;
                    }
                }

                x[d]++;

                createMesh(parentNode, backFace, u, v, side, x, du, dv, mask);
            }
        }
    }

    private void createMesh(Node parentNode, boolean backFace, int u, int v, int side, int[] x, int[] du, int[] dv, VoxelFace[] mask) {
        int n;
        int j;
        int i;
        int width;
        int height;
        int l;
        int k;
        n = 0;
        for (j = 0; j < CHUNK_HEIGHT; j++) {
            for (i = 0; i < CHUNK_WIDTH; ) {
                if (mask[n] != null) {
                    width = getWidth(i, n, mask);
                    height = getHeight(j, width, n, mask);

                    if (isSolidFace(mask[n])) {
                        x[u] = i;
                        x[v] = j;

                        du[0] = 0;
                        du[1] = 0;
                        du[2] = 0;
                        du[u] = width;

                        dv[0] = 0;
                        dv[1] = 0;
                        dv[2] = 0;
                        dv[v] = height;

                        Geometry quad = buildQuad(backFace, side, x, du, dv, mask, n);
                        parentNode.attachChild(quad);
                    }

                    for (l = 0; l < height; ++l) {
                        for (k = 0; k < width; ++k) {
                            mask[n + k + l * CHUNK_WIDTH] = null;
                        }
                    }

                    i += width;
                    n += width;
                } else {
                    i++;
                    n++;
                }
            }
        }
    }

    private Geometry buildQuad(boolean backFace, int side, int[] x, int[] du, int[] dv, VoxelFace[] mask, int n) {
        Vector3f bottomLeft = new Vector3f(x[0], x[1], x[2]);
        Vector3f topLeft = new Vector3f(x[0] + du[0], x[1] + du[1], x[2] + du[2]);
        Vector3f topRight = new Vector3f(x[0] + du[0] + dv[0], x[1] + du[1] + dv[1], x[2] + du[2] + dv[2]);
        Vector3f bottomRight = new Vector3f(x[0] + dv[0], x[1] + dv[1], x[2] + dv[2]);
        Geometry quad;
        if (backFace) {
            quad = this.quadFactory.buildBack(bottomLeft, topLeft, topRight, bottomRight, mask[n], side);
        } else {
            quad = this.quadFactory.buildFront(bottomLeft, topLeft, topRight, bottomRight, mask[n], side);
        }
        return quad;
    }

    private boolean isSolidFace(VoxelFace voxelFace) {
        return !voxelFace.isTransparent();
    }

    private int getHeight(int j, int width, int n, VoxelFace[] mask) {
        int height;
        int k;
        boolean done = false;

        for (height = 1; j + height < CHUNK_HEIGHT; height++) {

            for (k = 0; k < width; k++) {

                if (mask[n + k + height * CHUNK_WIDTH] == null ||
                        !mask[n + k + height * CHUNK_WIDTH].equals(mask[n])) {
                    done = true;
                    break;
                }
            }

            if (done) {
                break;
            }
        }
        return height;
    }

    private int getWidth(int i, int n, VoxelFace[] mask) {
        int width;
        for (width = 1;
             i + width < CHUNK_WIDTH && mask[n + width] != null &&
                     mask[n + width].equals(mask[n]);
             width++) {
        }
        return width;
    }

    private int getSide(boolean backFace, int side, int d) {
        if (d == 0) {
            side = backFace ? VoxelChunk.SIDE_X_NEG : VoxelChunk.SIDE_X_POS;
        } else if (d == 1) {
            side = backFace ? VoxelChunk.SIDE_Y_NEG : VoxelChunk.SIDE_Y_POS;
        } else if (d == 2) {
            side = backFace ? VoxelChunk.SIDE_Z_NEG : VoxelChunk.SIDE_Z_POS;
        }
        return side;
    }
}
