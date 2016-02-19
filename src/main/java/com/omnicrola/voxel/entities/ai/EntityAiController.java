package com.omnicrola.voxel.entities.ai;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 * Created by omnic on 1/17/2016.
 */
public class EntityAiController extends AbstractControl {

    private IAiState currentState;
    private AiStateMap states;
    private Vector3f currentTargetLocation;
    private Spatial currentTarget;

    public EntityAiController(AiStateMap states, IAiState initialState) {
        this.states = states;
        this.currentState = initialState;
    }

    public <T extends IAiState> T setState(Class<T> stateToken) {
        this.currentState = this.states.get(stateToken);
        return (T) this.currentState;
    }

    @Override
    protected void controlUpdate(float tpf) {
        this.currentState.update(this, tpf);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }


}
