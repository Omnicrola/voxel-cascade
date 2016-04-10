package com.omnicrola.voxel.eventBus.events;

/**
 * Created by Eric on 4/10/2016.
 */
public class LoadingStatusChangeEvent {
    private String status;
    private float percentComplete;

    public LoadingStatusChangeEvent(String status, float percentComplete) {
        this.status = status;
        this.percentComplete = percentComplete;
    }

    public String getStatus() {
        return status;
    }

    public float getPercentComplete() {
        return percentComplete;
    }
}
