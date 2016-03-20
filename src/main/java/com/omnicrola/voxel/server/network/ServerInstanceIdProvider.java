package com.omnicrola.voxel.server.network;

/**
 * Created by Eric on 3/20/2016.
 */
public enum ServerInstanceIdProvider {
    INSTANCE;

    private int currentId = 1;

    public synchronized int getNextId() {
        int nextId = this.currentId;
        this.currentId++;
        return nextId;
    }
}
