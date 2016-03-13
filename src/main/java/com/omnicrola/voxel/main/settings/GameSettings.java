package com.omnicrola.voxel.main.settings;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by omnic on 3/12/2016.
 */
@XmlRootElement(name = "Settings")
public class GameSettings {

    @XmlElement(name = "Display")
    public DisplaySettings displaySettings = new DisplaySettings();
}
