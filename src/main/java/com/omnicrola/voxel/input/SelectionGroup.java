package com.omnicrola.voxel.input;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.SelectedSpatial;
import com.omnicrola.voxel.entities.control.EntityCommandController;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.ui.select.ISelectedUnit;
import com.omnicrola.voxel.util.VoxelUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

/**
 * Created by omnic on 1/24/2016.
 */
public class SelectionGroup {
    private CursorCommandAdaptor cursorCommandAdaptor;
    private List<Spatial> selection;

    public SelectionGroup(CursorCommandAdaptor cursorStrategyFactory, List<Spatial> selection) {
        this.cursorCommandAdaptor = cursorStrategyFactory;
        this.selection = selection;
    }

    public SelectionGroup() {
        this(null, new ArrayList<>());
    }

    public int count() {
        return this.selection.size();
    }

    public List<ISelectedUnit> getSelections() {
        return this.selection
                .stream()
                .map(s -> new SelectedSpatial(s))
                .collect(Collectors.toList());
    }

    public List<CommandGroup> getAvailableCommands() {
        CommandCollector commandCollector = new CommandCollector(this.count());
        this.selection.stream()
                .map(u -> u.getControl(EntityCommandController.class))
                .filter(cc -> cc != null)
                .forEach(cc -> cc.collectCommands(commandCollector));
        return commandCollector.getCommandsCommonToAllEntities(this, this.cursorCommandAdaptor);
    }

    public List<CommandGroup> getBuildCommands() {
        CommandCollector commandCollector = new CommandCollector(this.count());
        this.selection.stream()
                .map(u -> u.getControl(EntityCommandController.class))
                .filter(cc -> cc != null)
                .forEach(cc -> cc.collectBuildCommands(commandCollector));
        return commandCollector.getCommandsCommonToAllEntities(this, cursorCommandAdaptor);
    }


    public Vector3f getCenterPoint() {
        if (this.selection.size() <= 0) {
            return new Vector3f();
        }
        float averageX = getAsDouble(Vector3f::getX);
        float averageY = getAsDouble(Vector3f::getY);
        float averageZ = getAsDouble(Vector3f::getZ);
        return new Vector3f(averageX, averageY, averageZ);
    }

    private float getAsDouble(ToDoubleFunction<Vector3f> axis) {
        return (float) this.selection.stream()
                .map(s -> s.getWorldTranslation())
                .mapToDouble(axis)
                .average()
                .getAsDouble();
    }

    public List<ISelectedUnit> update(float tpf) {
        List<ISelectedUnit> removedUnits = this.selection
                .stream()
                .filter(s -> VoxelUtil.isDead(s))
                .map(s -> new SelectedSpatial(s))
                .collect(Collectors.toList());
        this.selection = this.selection
                .stream()
                .filter(s -> VoxelUtil.isAlive(s))
                .collect(Collectors.toList());
        return removedUnits;
    }

    public int[] getUnitIds() {
        int[] ids = new int[selection.size()];
        for (int i = 0; i < selection.size(); i++) {
            Spatial spatial = this.selection.get(i);
            ids[i] = VoxelUtil.integerData(spatial, EntityDataKeys.WORLD_ID);
        }
        return ids;
    }
}
