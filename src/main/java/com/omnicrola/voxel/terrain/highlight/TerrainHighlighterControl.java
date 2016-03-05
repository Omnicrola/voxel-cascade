package com.omnicrola.voxel.terrain.highlight;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.entities.control.resources.VoxelQueue;
import com.omnicrola.voxel.entities.control.resources.VoxelDataHarvestComparator;
import com.omnicrola.voxel.input.IWorldCursor;
import com.omnicrola.voxel.terrain.data.VoxelData;

import java.util.List;

/**
 * Created by Eric on 3/2/2016.
 */
public class TerrainHighlighterControl extends AbstractControl implements ITerrainHighlighter {

    private Vector3f startLocation;
    private IWorldCursor worldCursor;
    private HighlighterCubeCache highlighterCubeCache;
    private ITerrainHighlightStrategy highlightStrategy;
    private Node parentNode;

    public TerrainHighlighterControl(IWorldCursor worldCursor,
                                     HighlighterCubeCache highlighterCubeCache,
                                     ITerrainHighlightStrategy highlightStrategy) {
        this.worldCursor = worldCursor;
        this.highlighterCubeCache = highlighterCubeCache;
        this.highlightStrategy = highlightStrategy;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        this.parentNode = (Node) spatial;
    }

    @Override
    public void setVisible(boolean isVisible) {
        Spatial.CullHint hint = (isVisible) ? Spatial.CullHint.Inherit : Spatial.CullHint.Always;
        this.spatial.setCullHint(hint);
    }

    @Override
    public void setStart(Vector3f location) {
        this.startLocation = location;
    }

    @Override
    public VoxelQueue getSelection(Vector3f endPoint) {
        List<VoxelData> selectedVoxels = this.highlightStrategy.findAllVoxelsInSelection(this.startLocation, endPoint);
        VoxelQueue voxelQueue = new VoxelQueue(selectedVoxels, new VoxelDataHarvestComparator(this.startLocation));
        return voxelQueue;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (isActive()) {
            this.highlighterCubeCache.reset();
            Vector3f currentLocation = this.worldCursor.getSnappedLocation().asVector3f();
            List<VoxelData> selectedVoxels = this.highlightStrategy.findAllVoxelsInSelection(this.startLocation, currentLocation);
            selectedVoxels.forEach(v -> addSelectionCube(v.getLocation()));
        }
    }

    private void addSelectionCube(Vector3f voxelLocation) {
        Geometry cube = this.highlighterCubeCache.next();
        cube.setLocalTranslation(voxelLocation.addLocal(0.5f, 0.5f, 0.5f));
        this.parentNode.attachChild(cube);
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
