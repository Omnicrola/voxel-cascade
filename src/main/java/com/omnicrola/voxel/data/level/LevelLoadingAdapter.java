package com.omnicrola.voxel.data.level;

/**
 * Created by omnic on 2/28/2016.
 */
public class LevelLoadingAdapter {
    private LevelStateLoader stateLoader;

    public LevelStateLoader getLoader() {
        return this.stateLoader;
    }

    public void setStateLoader(LevelStateLoader stateLoader) {
        this.stateLoader = stateLoader;
    }
}
