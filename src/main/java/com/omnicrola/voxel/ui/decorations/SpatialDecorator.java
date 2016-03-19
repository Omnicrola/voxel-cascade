package com.omnicrola.voxel.ui.decorations;

import com.jme3.scene.Node;
import com.omnicrola.voxel.entities.Effect;
import com.omnicrola.voxel.ui.decorations.hp.HealthBar;
import com.omnicrola.voxel.ui.decorations.hp.HealthBarFactory;
import com.omnicrola.voxel.ui.decorations.selected.SelectionRing;
import com.omnicrola.voxel.ui.decorations.selected.SelectionRingFactory;
import com.omnicrola.voxel.ui.select.ISelectedUnit;
import com.omnicrola.voxel.world.WorldManager;

import java.util.HashMap;

/**
 * Created by Eric on 3/18/2016.
 */
public class SpatialDecorator implements ISpatialDecorator {

    private final HashMap<ISelectedUnit, HealthBar> healthbars;
    private final HashMap<ISelectedUnit, SelectionRing> selectionRings;
    private WorldManager worldManager;
    HealthBarFactory healthBarFactory;
    private SelectionRingFactory selectionRingFactory;

    public SpatialDecorator(WorldManager worldManager, HealthBarFactory healthBarFactory,
                            SelectionRingFactory selectionRingFactory) {
        this.worldManager = worldManager;
        this.healthBarFactory = healthBarFactory;
        this.selectionRingFactory = selectionRingFactory;
        this.healthbars = new HashMap<>();
        this.selectionRings = new HashMap<>();
    }

    @Override
    public void addSelectionDecorations(ISelectedUnit selectedUnit) {
        HealthBar healthBar = this.healthBarFactory.build();
        this.worldManager.addEffect(new Effect(healthBar));
        selectedUnit.addDecoration(healthBar);
        this.healthbars.put(selectedUnit, healthBar);

        SelectionRing selectionRing =  this.selectionRingFactory.build();
        this.worldManager.addEffect(new Effect(selectionRing));
        selectedUnit.addDecoration(selectionRing);
        this.selectionRings.put(selectedUnit, selectionRing);
    }

    @Override
    public void removeSelectionDecorations(ISelectedUnit selectedUnit) {
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
