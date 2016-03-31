package com.omnicrola.voxel.entities.control.fx;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.entities.Effect;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.entities.control.IControlFactory;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by omnic on 3/13/2016.
 */
@XmlRootElement(name = "particle-effect")
public class ParticleEffectControlFactory implements IControlFactory {

    public ParticleEffectControlFactory() {
    }

    @Override
    public void build(Spatial spatial, UnitDefinitionRepository unitDefinitionRepository, EntityControlAdapter entityControlAdapter) {
        Effect effect = entityControlAdapter.getParticleBuilder().cubicHarvest();

        Vector3f offset = new Vector3f(0f, 0f, 0f);
        ParticleAttachmentControl particleAttachmentControl = new ParticleAttachmentControl(effect, entityControlAdapter, offset);
        spatial.addControl(particleAttachmentControl);
    }
}
