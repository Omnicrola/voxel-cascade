package com.omnicrola.voxel.ui;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.omnicrola.voxel.physics.GroundVehicleControl;

import java.text.DecimalFormat;

/**
 * Created by omnic on 1/16/2016.
 */
public class UserInterface extends Node {
    private final GLabel selectedLabel;
    private final GLabel positionLabel;
    private final DecimalFormat numberFormat;
    private GLabel velocityLabel;
    private Geometry selectedEntity;

    public UserInterface(GLabel selectedLabel, GLabel positionLabel, GLabel velocityLabel) {
        this.numberFormat = new DecimalFormat("#,##0.000");
        this.selectedLabel = selectedLabel;
        this.positionLabel = positionLabel;
        this.velocityLabel = velocityLabel;
    }

    public void setSelectedEntity(Geometry selectedEntity) {
        this.selectedEntity = selectedEntity;
    }

    @Override
    public void updateLogicalState(float tpf) {
        if (this.selectedEntity != null) {
            Vector3f worldTranslation = this.selectedEntity.getWorldTranslation();
            String name = this.selectedEntity.getName();
            this.selectedLabel.setText("Selected: " + name);
            this.positionLabel.setText("P: " + format(worldTranslation));
            Vector3f linearVelocity = this.selectedEntity.getControl(GroundVehicleControl.class).getWalkDirection();
            this.velocityLabel.setText("V: " + format(linearVelocity));
        } else {
            this.selectedLabel.setText("Selected: ");
            this.positionLabel.setText("P: ");
            this.velocityLabel.setText("V: ");
        }
        super.updateLogicalState(tpf);
    }

    private String format(Vector3f vector) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(numberFormat.format(vector.x));
        stringBuilder.append(",");
        stringBuilder.append(numberFormat.format(vector.y));
        stringBuilder.append(",");
        stringBuilder.append(numberFormat.format(vector.z));
        return stringBuilder.toString();
    }
}
