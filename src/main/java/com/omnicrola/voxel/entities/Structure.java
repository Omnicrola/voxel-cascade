package com.omnicrola.voxel.entities;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Created by Eric on 2/25/2016.
 */
public class Structure extends AbstractGameEntity {
    public Structure(Spatial spatial) {
        super(spatial);
    }

    @Override
    public void setLocation(Vector3f location) {
        spatial.setLocalTranslation(location);
    }
}
