package com.omnicrola.voxel.debug;

/**
 * Created by omnic on 2/27/2016.
 */
public class DebugTimer {
    private long time;

    public DebugTimer() {
        reset();
    }

    public void reset() {
        this.time = System.nanoTime();
    }

    public float mark() {
        long end = System.nanoTime();
        float elapsedMillis = (end - this.time) / 1_000_000f;
        return elapsedMillis;
    }
}
