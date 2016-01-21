package com.omnicrola.voxel.data.units;

import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import com.omnicrola.voxel.data.GameXmlDataParser;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Eric on 1/18/2016.
 */
public class EntityDefinitionXmlAssetLoader implements AssetLoader {

    private final GameXmlDataParser definitionLoader;

    public EntityDefinitionXmlAssetLoader() {
        this.definitionLoader = new GameXmlDataParser();
    }

    @Override
    public Object load(AssetInfo assetInfo) throws IOException {
        InputStream inputStream = assetInfo.openStream();
        UnitDefinitionRepository definitionLibrary = this.definitionLoader
                .loadDefinitions(inputStream);
        return definitionLibrary;
    }

}
