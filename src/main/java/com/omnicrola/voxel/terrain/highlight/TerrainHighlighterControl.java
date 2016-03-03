package com.omnicrola.voxel.terrain.highlight;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.input.IWorldCursor;
import com.omnicrola.voxel.terrain.ITerrainManager;

/**
 * Created by Eric on 3/2/2016.
 */
public class TerrainHighlighterControl extends AbstractControl implements ITerrainHighlighter {

    private Vector3f startLocation;
    private IWorldCursor worldCursor;
    private ITerrainManager terrainManager;
    private HighlighterCubeCache highlighterCubeCache;
    private Node parentNode;

    public TerrainHighlighterControl(IWorldCursor worldCursor,
                                     ITerrainManager terrainManager,
                                     HighlighterCubeCache highlighterCubeCache) {
        this.worldCursor = worldCursor;
        this.terrainManager = terrainManager;
        this.highlighterCubeCache = highlighterCubeCache;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        this.parentNode = (Node) spatial;
    }

    public void setVisible(boolean isVisible) {
        Spatial.CullHint hint = (isVisible) ? Spatial.CullHint.Inherit : Spatial.CullHint.Always;
        this.spatial.setCullHint(hint);
    }

    public void setStart(Vector3f location) {
        this.startLocation = location;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (isActive()) {
            this.highlighterCubeCache.reset();
            Vec3i currentLocation = this.worldCursor.getSnappedLocation();
            float startX = Math.min(startLocation.x, currentLocation.getX());
            float startZ = Math.min(startLocation.z, currentLocation.getZ());
            float endX = Math.max(startLocation.x, currentLocation.getX());
            float endZ = Math.max(startLocation.z, currentLocation.getZ());
            Vector3f location = new Vector3f();

            for (float x = startX; x <= endX; x++) {
                for (float z = startZ; z <= endZ; z++) {
                    location.set(x, this.startLocation.y, z);
                    Vec3i voxelLocation = Vec3i.round(this.terrainManager.getHighestSolidVoxel(location));
                    Geometry cube = this.highlighterCubeCache.next();
                    cube.setLocalTranslation(voxelLocation.asVector3f().addLocal(0.5f , 0.5f, 0.5f));
                    this.parentNode.attachChild(cube);
                }
            }
        }
    }

    private boolean isActive() {
        boolean isNotHidden = !this.spatial.getCullHint().equals(Spatial.CullHint.Always);
        boolean hasStarted = this.startLocation != null;
        return isNotHidden && hasStarted;
    }

    @Override
    public void clear() {
        this.highlighterCubeCache.reset();
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
