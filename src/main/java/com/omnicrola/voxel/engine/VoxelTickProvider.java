package com.omnicrola.voxel.engine;

/**
 * Created by Eric on 2/22/2016.
 */
public class VoxelTickProvider implements ITickProvider {

    private static int TIC_INTERVAL = 250;

    private long tic;
    private float elapsed;

    public VoxelTickProvider() {
        this.elapsed = 0;
    }

    @Override
    public long getTic() {
        return this.tic;
    }

    public void update(float tpf) {
        this.elapsed += tpf;
        if (this.elapsed >= TIC_INTERVAL) {
            int tics = (int) Math.floor(this.elapsed / TIC_INTERVAL);
            this.elapsed = tics * TIC_INTERVAL;
            this.tic += tics;
        }
    }
}
