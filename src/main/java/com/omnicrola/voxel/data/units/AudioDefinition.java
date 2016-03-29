package com.omnicrola.voxel.data.units;

import com.jme3.audio.AudioData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Eric on 3/28/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AudioDefinition {

    @XmlElement(name = "filename", required = true)
    protected String filename;

    @XmlElement(name = "type")
    protected AudioData.DataType type = AudioData.DataType.Buffer;

    @XmlAttribute(name="volume")
    protected float volume = 1.0f;

    public AudioDefinition() {
    }

    public AudioDefinition(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public AudioData.DataType getType() {
        return type;
    }

    public float getVolume() {
        return volume;
    }
}
