package com.omnicrola.voxel.input;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.SelectedSpatial;
import com.omnicrola.voxel.entities.ai.*;
import com.omnicrola.voxel.entities.commands.IConstructionPackage;
import com.omnicrola.voxel.entities.control.EntityCommandController;
import com.omnicrola.voxel.entities.control.MotionGovernorControl;
import com.omnicrola.voxel.entities.resources.VoxelHarvestTarget;
import com.omnicrola.voxel.ui.ISelectedUnit;
import com.omnicrola.voxel.util.VoxelUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by omnic on 1/24/2016.
 */
public class SelectionGroup {
    private final NavigationGridDistributor gridDistributor;
    private CursorCommandDelegator cursorCommandDelegator;
    private List<Spatial> selection;

    public SelectionGroup(CursorCommandDelegator cursorStrategyFactory, List<Spatial> selection) {
        this.cursorCommandDelegator = cursorStrategyFactory;
        this.selection = selection;
        this.gridDistributor = new NavigationGridDistributor(this);
    }

    public SelectionGroup() {
        this(null, new ArrayList<>());
    }

    public int count() {
        return this.selection.size();
    }

    public void orderMoveToLocation(Vector3f vector3f) {
        Iterator<Vector3f> navPoints = this.gridDistributor.distribute(vector3f);
        getEntityAiControllerStream().forEach(ai -> ai.setState(AiMoveToLocationState.class).setTarget(navPoints.next()));
    }

    public void orderStop() {
        getEntityAiControllerStream().forEach(ai -> ai.setState(AiStopState.class));
    }

    public void orderAttackTarget(Geometry target) {
        getEntityAiControllerStream().forEach(ai -> ai.setState(AiAttackTargetState.class).setTarget(target));
    }

    public void orderAttackLocation(Vector3f location) {
        Iterator<Vector3f> navPoints = this.gridDistributor.distribute(location);
        getEntityAiControllerStream().forEach(ai -> ai.setState(AiMoveToLocationState.class).setTarget(navPoints.next()));
    }

    public void orderHarvest(VoxelHarvestTarget voxelHarvestTarget) {
        getEntityAiControllerStream().forEach(ai -> ai.setState(AiHarvestState.class).setTarget(voxelHarvestTarget));
    }

    public void orderBuild(IConstructionPackage constructionPackage) {
        Optional<EntityAiController> firstController = getEntityAiControllerStream().findFirst();
        if(firstController.isPresent()){
            EntityAiController entityAiController = firstController.get();
            entityAiController.setState(AiBuildState.class).setPackage(constructionPackage);
        }
    }

    public float getLargestUnitSize() {
        Optional<Float> largest = this.selection
                .stream()
                .map(u -> getPersonalRadius(u))
                .sorted()
                .findFirst();
        if (largest.isPresent()) {
            return largest.get();
        } else {
            return 1.0f;
        }
    }

    private float getPersonalRadius(Spatial spatial) {
        MotionGovernorControl motionControl = spatial.getControl(MotionGovernorControl.class);
        if (motionControl == null) {
            return 0;
        } else {
            return motionControl.getPersonalRadius();
        }
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
        return commandCollector.getCommandsCommonToAllEntities(this, this.cursorCommandDelegator);
    }

    public List<CommandGroup> getBuildCommands() {
        CommandCollector commandCollector = new CommandCollector(this.count());
        this.selection.stream()
                .map(u -> u.getControl(EntityCommandController.class))
                .filter(cc -> cc != null)
                .forEach(cc -> cc.collectBuildCommands(commandCollector));
        return commandCollector.getCommandsCommonToAllEntities(this, cursorCommandDelegator);
    }

    private Stream<EntityAiController> getEntityAiControllerStream() {
        return this.selection
                .stream()
                .map(u -> getAi(u))
                .filter(ai -> ai != null);
    }

    private EntityAiController getAi(Spatial spatial) {
        return spatial.getControl(EntityAiController.class);
    }

    public Vector3f getCenterPoint() {
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

    public boolean update(float tpf) {
        int currentSize = this.selection.size();
        this.selection = this.selection
                .stream()
                .filter(s -> VoxelUtil.isAlive(s))
                .collect(Collectors.toList());
        return currentSize != this.selection.size();
    }


}
