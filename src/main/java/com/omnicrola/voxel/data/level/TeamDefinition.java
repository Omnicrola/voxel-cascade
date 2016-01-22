package com.omnicrola.voxel.data.level;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by Eric on 1/21/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamDefinition {

    @XmlAttribute(name = "team-id", required = true)
    protected int id;

    public TeamDefinition() {
    }

    public int getId() {
        return id;
    }
}
