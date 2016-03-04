package com.omnicrola.voxel.terrain.highlight;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.entities.control.resources.HarvestQueue;
import com.omnicrola.voxel.entities.control.resources.VoxelDataHarvestComparator;
import com.omnicrola.voxel.input.IWorldCursor;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.terrain.data.VoxelData;

import java.util.ArrayList;
import java.util.Optional;

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
    public HarvestQueue getSelection(Vector3f endPoint) {
        ArrayList<VoxelData> selectedVoxels = findAllVoxelsInSelection(Vec3i.round(endPoint));
        return new HarvestQueue(selectedVoxels, new VoxelDataHarvestComparator(this.startLocation));
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (isActive()) {
            this.highlighterCubeCache.reset();
            Vec3i currentLocation = this.worldCursor.getSnappedLocation();
            ArrayList<VoxelData> selectedVoxels = findAllVoxelsInSelection(currentLocation);
            selectedVoxels.forEach(v -> addSelectionCube(v.getLocation()));
        }
    }

    private void addSelectionCube(Vector3f voxelLocation) {
        Geometry cube = this.highlighterCubeCache.next();
        cube.setLocalTranslation(voxelLocation.addLocal(0.5f, 0.5f, 0.5f));
        this.parentNode.attachChild(cube);
    }

    private ArrayList<VoxelData> findAllVoxelsInSelection(Vec3i endLocation) {
        float startX = Math.min(startLocation.x, endLocation.getX());
        float startZ = Math.min(startLocation.z, endLocation.getZ());
        float endX = Math.max(startLocation.x, endLocation.getX());
        float endZ = Math.max(startLocation.z, endLocation.getZ());
        Vector3f location = new Vector3f();

        ArrayList<VoxelData> voxels = new ArrayList<>();
        for (float x = startX; x <= endX; x++) {
            for (float z = startZ; z <= endZ; z++) {
                location.set(x, this.startLocation.y, z);
                Optional<VoxelData> highestSolidVoxel = this.terrainManager.getHighestSolidVoxel(location);
                if (highestSolidVoxel.isPresent()) {
                    voxels.add(highestSolidVoxel.get());
                }
            }
        }
        return voxels;
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
