package com.omnicrola.voxel.world;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamId;

/**
 * Created by Eric on 2/22/2016.
 */
public interface IGameEntity {
    Spatial getSpatial();

    TeamId getTeam();

    boolean isAlive();

    boolean matches(Spatial spatial);
}
