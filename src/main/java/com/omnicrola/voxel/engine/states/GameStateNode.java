package com.omnicrola.voxel.engine.states;

import com.jme3.light.Light;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.level.LevelState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omnic on 1/23/2016.
 */
public class GameStateNode {
    private Spatial terrain;
    private Spatial units;
    private List<Light> lights = new ArrayList<>();

    public GameStateNode() {
        this.terrain = new Node();
        this.units = new Node();
    }

    public GameStateNode(LevelState levelDefinition) {
        this.terrain = levelDefinition.getTerrain();
        this.units = levelDefinition.getUnits();
        this.lights.add(levelDefinition.getSun());
    }

    public Spatial getTerrain() {
        return terrain;
    }

    public Spatial getUnits() {
        return units;
    }

    public List<Light> getLights() {
        return lights;
    }
}
