package com.omnicrola.voxel.entities.behavior.ai;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 * Created by omnic on 1/17/2016.
 */
public class EntityAiController extends AbstractControl {

    private IAiState currentState;
    private AiStateMap states;

    public EntityAiController(AiStateMap states, IAiState initialState) {
        this.states = states;
        this.currentState = initialState;
    }

    public <T extends IAiState> T setState(Class<T> stateToken) {
        this.currentState = this.states.get(stateToken);
        System.out.println("set state: " + this.currentState);
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
