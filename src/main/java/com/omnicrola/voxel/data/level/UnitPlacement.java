package com.omnicrola.voxel.data.level;

import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import com.omnicrola.voxel.data.VectorXmlTypeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by Eric on 1/18/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Serializable
public class UnitPlacement {

    @XmlElement(name = "unit-id", required = true)
    protected int unitId;

    @XmlElement(name = "team-id", required = true)
    protected int teamId;

    @XmlElement(name = "location", required = true)
    @XmlJavaTypeAdapter(VectorXmlTypeAdapter.class)
    protected Vector3f location;

    @XmlTransient
    // should only be set by the multiplayer server
    private int instanceId;

    public UnitPlacement() {
    }

    public UnitPlacement(int unitId, int teamId, Vector3f location) {
        this.unitId = unitId;
        this.teamId = teamId;
        this.location = location;
    }

    public int getUnitId() {
        return unitId;
    }

    public Vector3f getLocation() {
        return location;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(int instanceId) {
        this.instanceId = instanceId;
    }
}
