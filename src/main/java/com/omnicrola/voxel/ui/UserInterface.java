package com.omnicrola.voxel.ui;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.omnicrola.voxel.input.SelectionGroup;

import java.text.DecimalFormat;

/**
 * Created by omnic on 1/16/2016.
 */
public class UserInterface extends Node implements IGameUserInterface {
    private final GLabel selectedLabel;
    private final GLabel positionLabel;
    private final DecimalFormat numberFormat;
    private GLabel velocityLabel;
    private SelectionGroup selectedUnits;
    private boolean inBuildMode;

    public UserInterface(GLabel selectedLabel, GLabel positionLabel, GLabel velocityLabel) {
        this.numberFormat = new DecimalFormat("#,##0.000");
        this.selectedLabel = selectedLabel;
        this.positionLabel = positionLabel;
        this.velocityLabel = velocityLabel;
    }

    @Override
    public void updateLogicalState(float tpf) {
        if (this.selectedUnits != null) {
            String names = this.selectedUnits.unitNames();
            this.selectedLabel.setText("Selected: " + this.selectedUnits.count() + " " + names);
        } else {
            this.selectedLabel.setText("Selected: ");
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

    public void setSelectedUnits(SelectionGroup selectedUnits) {
        this.selectedUnits = selectedUnits;
    }

    @Override
    public boolean isInBuildMode() {
        return this.inBuildMode;
    }

    @Override
    public void setBuildMode(boolean buildMode) {
        this.inBuildMode = buildMode;
    }
}
