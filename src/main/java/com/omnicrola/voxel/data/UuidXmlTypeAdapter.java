package com.omnicrola.voxel.data;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.UUID;

/**
 * Created by Eric on 1/20/2016.
 */
public class UuidXmlTypeAdapter extends XmlAdapter<String, UUID> {
    @Override
    public UUID unmarshal(String serialUuid) throws Exception {
        return UUID.fromString(serialUuid);
    }

    @Override
    public String marshal(UUID uuid) throws Exception {
        return uuid.toString();
    }
}
