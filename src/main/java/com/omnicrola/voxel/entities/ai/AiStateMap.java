package com.omnicrola.voxel.entities.ai;

import java.util.HashMap;

/**
 * Created by Eric on 2/18/2016.
 */
public class AiStateMap extends HashMap<Class<? extends IAiState>, IAiState> {

    public void add(IAiState aiState){
        this.put(aiState.getClass(), aiState);
    }
}
