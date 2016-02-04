package com.omnicrola.voxel.terrain;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by Eric on 1/31/2016.
 */
public class VoxelChunkRebuilder {

    private static final int CHUNK_WIDTH = GameConstants.CHUNK_SIZE;
    private static final int CHUNK_HEIGHT = GameConstants.CHUNK_SIZE;
    public static final float VOXEL_SIZE = 1.0f;

    private IGameContainer gameContainer;
    private QuadFactory quadFactory;

    public VoxelChunkRebuilder(IGameContainer gameContainer, QuadFactory quadFactory) {
        this.gameContainer = gameContainer;
        this.quadFactory = quadFactory;
    }

    public void rebuild(VoxelChunk chunk) {
        chunk.detachAllChildren();

        int i, j, k, l, w, h, u, v, n, side = 0;

        final int[] x = new int[]{0, 0, 0};
        final int[] q = new int[]{0, 0, 0};
        final int[] du = new int[]{0, 0, 0};
        final int[] dv = new int[]{0, 0, 0};

        final VoxelFace[] mask = new VoxelFace[GameConstants.CHUNK_SIZE * GameConstants.CHUNK_SIZE];
        VoxelFace voxelFace, voxelFace1;

        for (boolean backFace = true, b = false; b != backFace; backFace = backFace && b, b = !b) {

            /*
             * We sweep over the 3 dimensions - most of what follows is well described by Mikola Lysenko
             * in his post - and is ported from his Javascript implementation.  Where this implementation
             * diverges, I've added commentary.
             */
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

                /*
                 * Here we're keeping track of the side that we're meshing.
                 */
                if (d == 0) {
                    side = backFace ? VoxelChunk.SIDE_WEST : VoxelChunk.SIDE_EAST;
                } else if (d == 1) {
                    side = backFace ? VoxelChunk.SIDE_BOTTOM : VoxelChunk.SIDE_TOP;
                } else if (d == 2) {
                    side = backFace ? VoxelChunk.SIDE_SOUTH : VoxelChunk.SIDE_NORTH;
                }

                /*
                 * We move through the dimension from front to back
                 */
                for (x[d] = -1; x[d] < CHUNK_WIDTH; ) {

                    /*
                     * -------------------------------------------------------------------
                     *   We compute the mask
                     * -------------------------------------------------------------------
                     */
                    n = 0;

                    for (x[v] = 0; x[v] < CHUNK_HEIGHT; x[v]++) {

                        for (x[u] = 0; x[u] < CHUNK_WIDTH; x[u]++) {

                            /*
                             * Here we retrieve two voxel faces for comparison.
                             */
                            voxelFace = (x[d] >= 0) ? chunk.getVoxelFace(x[0], x[1], x[2], side) : null;
                            voxelFace1 = (x[d] < CHUNK_WIDTH - 1) ? chunk.getVoxelFace(x[0] + q[0], x[1] + q[1], x[2] + q[2], side) : null;

                            /*
                             * Note that we're using the equals function in the voxel face class here, which lets the faces
                             * be compared based on any number of attributes.
                             *
                             * Also, we choose the face to add to the mask depending on whether we're moving through on a backface or not.
                             */
                            mask[n++] = ((voxelFace != null && voxelFace1 != null && voxelFace.equals(voxelFace1)))
                                    ? null
                                    : backFace ? voxelFace1 : voxelFace;
                        }
                    }

                    x[d]++;

                    /*
                     * Now we generate the mesh for the mask
                     */
                    n = 0;

                    for (j = 0; j < CHUNK_HEIGHT; j++) {

                        for (i = 0; i < CHUNK_WIDTH; ) {

                            if (mask[n] != null) {

                                /*
                                 * We compute the width
                                 */
                                for (w = 1; i + w < CHUNK_WIDTH && mask[n + w] != null && mask[n + w].equals(mask[n]); w++) {
                                }

                                /*
                                 * Then we compute height
                                 */
                                boolean done = false;

                                for (h = 1; j + h < CHUNK_HEIGHT; h++) {

                                    for (k = 0; k < w; k++) {

                                        if (mask[n + k + h * CHUNK_WIDTH] == null || !mask[n + k + h * CHUNK_WIDTH].equals(mask[n])) {
                                            done = true;
                                            break;
                                        }
                                    }

                                    if (done) {
                                        break;
                                    }
                                }

                                /*
                                 * Here we check the "transparent" attribute in the VoxelFace class to ensure that we don't mesh
                                 * any culled faces.
                                 */
                                if (!mask[n].transparent()) {
                                    /*
                                     * Add quad
                                     */
                                    x[u] = i;
                                    x[v] = j;

                                    du[0] = 0;
                                    du[1] = 0;
                                    du[2] = 0;
                                    du[u] = w;

                                    dv[0] = 0;
                                    dv[1] = 0;
                                    dv[2] = 0;
                                    dv[v] = h;

                                    /*
                                     * And here we call the quad function in order to render a merged quad in the scene.
                                     *
                                     * We pass mask[n] to the function, which is an instance of the VoxelFace class containing
                                     * all the attributes of the face - which allows for variables to be passed to shaders - for
                                     * example lighting values used to create ambient occlusion.
                                     */
                                    Geometry quad = this.quadFactory.build(new Vector3f(x[0], x[1], x[2]),
                                            new Vector3f(x[0] + du[0], x[1] + du[1], x[2] + du[2]),
                                            new Vector3f(x[0] + du[0] + dv[0], x[1] + du[1] + dv[1], x[2] + du[2] + dv[2]),
                                            new Vector3f(x[0] + dv[0], x[1] + dv[1], x[2] + dv[2]),
                                            mask[n],
                                            backFace);
                                    chunk.attachChild(quad);
                                }

                                /*
                                 * We zero out the mask
                                 */
                                for (l = 0; l < h; ++l) {

                                    for (k = 0; k < w; ++k) {
                                        mask[n + k + l * CHUNK_WIDTH] = null;
                                    }
                                }

                                /*
                                 * And then finally increment the counters and continue
                                 */
                                i += w;
                                n += w;
                            } else {

                                i++;
                                n++;
                            }
                        }
                    }
                }
            }
        }
        chunk.clearRebuildFlag();
    }


}
