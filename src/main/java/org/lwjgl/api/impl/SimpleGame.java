package org.lwjgl.api.impl;

import org.lwjgl.api.*;

import java.util.ArrayList;

/**
 * Created by omnic on 1/10/2016.
 */
public abstract class SimpleGame {
    private final ArrayList<IGameState> states;
    private  IGameContainer gameContainer;
    private boolean isRunning;

    public SimpleGame() {
        this.states = new ArrayList<>();
    }

    public void addState(IGameState state) {
        this.states.add(state);
    }

    public void start() {
        GLContextHandler glContext = GLContextBuilder.build();
        this.gameContainer = GameContainerBuilder.build(glContext);
        initializeStates();
        this.isRunning = true;
        loadStates();
        while (this.isRunning) {
            updateStates();
            renderStates();
        }
        unloadStates();
    }

    private void initializeStates() {
        for (IGameState state : this.states) {
            state.initialize(this.gameContainer);
        }
    }

    private void loadStates() {
        for (IGameState state : this.states) {
            state.load(this.gameContainer);
        }
    }

    private void unloadStates() {
        for (IGameState state : this.states) {
            state.unload(this.gameContainer);
        }
    }

    private void updateStates() {
        for (IGameState state : this.states) {
            state.update(this.gameContainer);
        }
    }

    private void renderStates() {
        for (IGameState state : this.states) {
            state.render(null);
        }
    }

    public void stop() {
        this.isRunning = false;
    }
}
