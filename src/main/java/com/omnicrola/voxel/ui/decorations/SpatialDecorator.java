package com.omnicrola.voxel.ui.decorations;

import com.jme3.scene.Node;
import com.omnicrola.voxel.entities.Effect;
import com.omnicrola.voxel.ui.decorations.hp.HealthBar;
import com.omnicrola.voxel.ui.decorations.hp.HealthBarFactory;
import com.omnicrola.voxel.ui.select.ISelectedUnit;
import com.omnicrola.voxel.world.WorldManager;

import java.util.HashMap;

/**
 * Created by Eric on 3/18/2016.
 */
public class SpatialDecorator implements ISpatialDecorator {

    private final HashMap<ISelectedUnit, HealthBar> healthbars;
    private WorldManager worldManager;
    HealthBarFactory healthBarFactory;

    public SpatialDecorator(WorldManager worldManager, HealthBarFactory healthBarFactory) {
        this.worldManager = worldManager;
        this.healthBarFactory = healthBarFactory;
        this.healthbars = new HashMap<>();
    }

    @Override
    public void addHealthbar(ISelectedUnit selectedUnit) {
        HealthBar healthBar = this.healthBarFactory.build();
        this.worldManager.addEffect(new Effect(healthBar));
        selectedUnit.addDecoration(healthBar);
        this.healthbars.put(selectedUnit, healthBar);
    }

    @Override
    public void removeHealthbar(ISelectedUnit selectedUnit) {
        HealthBar healthBar = this.healthbars.remove(selectedUnit);
        if (healthBar != null) {
            selectedUnit.removeDecoration(healthBar);
            Node parent = healthBar.getParent();
            if (parent != null) {
                parent.detachChild(healthBar);
            }
        }
    }
}
