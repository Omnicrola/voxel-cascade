package com.omnicrola.voxel.terrain.highlight;

import com.jme3.scene.Geometry;

import java.util.ArrayList;

/**
 * Created by Eric on 3/2/2016.
 */
public class HighlighterCubeCache {

    private final ArrayList<Geometry> cubes;
    private int index;
    private CubeFactory cubeFactory;

    public HighlighterCubeCache(CubeFactory cubeFactory) {
        this.cubeFactory = cubeFactory;
        this.cubes = new ArrayList<>();
    }

    public void reset() {
        this.cubes.forEach(c -> c.removeFromParent());
        this.index = 0;
    }

    public Geometry next() {
        if (index >= this.cubes.size()) {
            addCube();
        }
        Geometry geometry = this.cubes.get(this.index);
        this.index++;
        return geometry;
    }

    private void addCube() {
        Geometry cube = this.cubeFactory.build();
        this.cubes.add(cube);
    }
}
