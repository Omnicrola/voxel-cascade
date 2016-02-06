package com.omnicrola.voxel.entities.control;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;

/**
 * Created by omnic on 1/17/2016.
 */
public interface IControlFactory {
    public void build(Spatial spatial, IGameContainer gameContainer, UnitDefinitionRepository unitDefinitionRepository);
}
