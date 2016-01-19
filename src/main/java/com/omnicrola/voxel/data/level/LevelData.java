package com.omnicrola.voxel.data.level;

import com.omnicrola.util.Vec3i;
import com.omnicrola.util.Vec3iRead;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by omnic on 1/16/2016.
 */
@XmlRootElement(name = "Level")
public class LevelData {

    @XmlElement(name = "name", required = true)
    protected String name;

    @XmlElement(name = "terrain-dimensions", required = true)
    protected Vec3i terrainSize = new Vec3i(40, 5, 40);

    @XmlElement(name = "terrain-offset", required = true)
    protected Vec3i terrainOffset = new Vec3i(0, -5, 0);

    @XmlElementWrapper(name = "unit-placements", required = true)
    @XmlElement(name = "placement")
    protected List<UnitPlacement> unitPlacements;

    public LevelData() {
    }

    public String getName() {
        return name;
    }

    public Vec3iRead getTerrainOffset() {
        return terrainOffset;
    }

    public Vec3iRead getTerrainSize() {
        return terrainSize;
    }

    public List<UnitPlacement> getUnitPlacements() {
        return unitPlacements;
    }
}
