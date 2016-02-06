package com.omnicrola.voxel.data.level;

import com.omnicrola.util.Vec3i;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Eric on 2/5/2016.
 */
public class TerrainDefinition {

    @XmlAttribute(name = "width", required = true)
    protected int width;

    @XmlAttribute(name = "depth", required = true)
    protected int depth;

    @XmlAttribute(name = "vertical-scale")
    protected int verticalScale = 1;

    @XmlElement(name = "offset")
    protected Vec3i terrainOffset = new Vec3i(0, 0, 0);

    @XmlElement(name="octaves")
    protected int octaves;

    @XmlElement(name="seed")
    protected int seed;

    public int getWidth() {
        return width;
    }

    public int getDepth() {
        return depth;
    }

    public int getVerticalScale() {
        return verticalScale;
    }

    public Vec3i getTerrainOffset() {
        return terrainOffset;
    }

    public int getOctaves() {
        return octaves;
    }

    public int getSeed() {
        return seed;
    }
}
