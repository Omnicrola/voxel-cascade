package com.omnicrola.voxel.main.settings;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by omnic on 3/12/2016.
 */
public class DisplaySettings {

    @XmlElement(name = "width")
    public int width = 800;

    @XmlElement(name = "height")
    public int height = 600;

    @XmlElement(name = "max-fps")
    public int maxFps = 120;

    @XmlElement(name = "samples")
    public int antiAliasing = 4;

    @XmlElement(name="fullscreen")
    public boolean fullscreen;
}
