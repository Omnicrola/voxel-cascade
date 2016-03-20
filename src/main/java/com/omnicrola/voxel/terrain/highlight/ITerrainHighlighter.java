package com.omnicrola.voxel.terrain.highlight;

import com.jme3.math.Vector3f;
import com.omnicrola.util.Vec3i;

import java.util.List;

/**
 * Created by Eric on 3/2/2016.
 */
public interface ITerrainHighlighter {
    void setVisible(boolean isVisible);

    void setStart(Vector3f location);

    List<Vec3i> getSelection(Vector3f endPoint);

    void clear();
}
