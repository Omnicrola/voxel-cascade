package com.omnicrola.voxel.entities.control.old;

import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.input.SelectionGroup;

/**
 * Created by Eric on 2/7/2016.
 */
public class BuildCursorValidityControl extends AbstractControl {
    private final SelectionGroup selectionGroup;
    private final float buildRadius;
    private final Material validBuildMaterial;
    private final Material invalidBuildMaterial;
    private boolean isValidLocation;
    private Material currentMaterial;

    public BuildCursorValidityControl(SelectionGroup selectionGroup,
                                      float buildRadius,
                                      Material validBuildMaterial,
                                      Material invalidBuildMaterial) {
        this.selectionGroup = selectionGroup;
        this.buildRadius = buildRadius;
        this.validBuildMaterial = validBuildMaterial;
        this.invalidBuildMaterial = invalidBuildMaterial;
        this.currentMaterial = invalidBuildMaterial;
    }

    @Override
    protected void controlUpdate(float tpf) {
        Vector3f groupCenter = this.selectionGroup.getCenterPoint();
        Vector3f worldTranslation = this.spatial.getWorldTranslation();
        float distance = groupCenter.distance(worldTranslation);
        if (distance > this.buildRadius) {
            setMaterial(this.invalidBuildMaterial);
            this.isValidLocation = false;
        } else {
            setMaterial(this.validBuildMaterial);
            this.isValidLocation = true;
        }
    }

    private void setMaterial(Material material) {
        if (this.currentMaterial != material) {
            this.spatial.setMaterial(material);
            this.currentMaterial = material;
        }
    }


    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
    }

    public boolean isValidLocation() {
        return this.isValidLocation;
    }
}
