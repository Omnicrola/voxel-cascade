package com.omnicrola.voxel.data.units;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Eric on 3/28/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class EntityAudioDefinition {

    @XmlElement(name = "ambient")
    protected AudioDefinition ambientLoop;

    @XmlElement(name = "death")
    protected AudioDefinition deathSound;

    @XmlElement(name = "spawn")
    protected AudioDefinition spawnSound;

    @XmlElement(name = "take-damage")
    protected AudioDefinition takeDamageSound;

    public EntityAudioDefinition() {
    }

    public AudioDefinition getAmbientLoop() {
        return ambientLoop;
    }

    public AudioDefinition getDeathSound() {
        return deathSound;
    }

    public AudioDefinition getSpawnSound() {
        return spawnSound;
    }

    public AudioDefinition getTakeDamageSound() {
        return takeDamageSound;
    }
}
