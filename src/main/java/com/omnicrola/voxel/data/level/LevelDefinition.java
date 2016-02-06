package com.omnicrola.voxel.data.level;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.omnicrola.voxel.data.QuaternionXmlTypeAdapter;
import com.omnicrola.voxel.data.UuidXmlTypeAdapter;
import com.omnicrola.voxel.data.VectorXmlTypeAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by omnic on 1/16/2016.
 */
@XmlRootElement(name = "Level")
public class LevelDefinition {

    @XmlElement(name = "name", required = true)
    protected String name;

    @XmlElement(name = "global-id")
    @XmlJavaTypeAdapter(UuidXmlTypeAdapter.class)
    protected UUID uuid;

    @XmlElement(name = "camera-start-position", required = true)
    @XmlJavaTypeAdapter(VectorXmlTypeAdapter.class)
    protected Vector3f cameraPosition;

    @XmlElement(name = "camera-orientation")
    @XmlJavaTypeAdapter(QuaternionXmlTypeAdapter.class)
    protected Quaternion cameraOrientation;

    @XmlElement(name="terrain")
    protected TerrainDefinition terrain = new TerrainDefinition();

    @XmlElementWrapper(name = "unit-placements", required = true)
    @XmlElement(name = "placement")
    protected List<UnitPlacement> unitPlacements;


    @XmlElementWrapper(name = "structure-placements", required = true)
    @XmlElement(name = "placement")
    public List<UnitPlacement> structurePlacements;

    @XmlElementWrapper(name = "teams")
    @XmlElement(name = "team")
    protected List<TeamDefinition> teams = new ArrayList<>();

    public LevelDefinition() {
    }

    public String getName() {
        return name;
    }

    public List<UnitPlacement> getUnitPlacements() {
        return unitPlacements;
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<TeamDefinition> getTeams() {
        return teams;
    }

    public List<UnitPlacement> getStructures() {
        return this.structurePlacements;
    }

    public Vector3f getCameraPosition() {
        return this.cameraPosition;
    }

    public Quaternion getCameraOrientation() {
        return this.cameraOrientation;
    }

    public TerrainDefinition getTerrain() {
        return terrain;
    }
}
