package com.omnicrola.voxel.data.units;

import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import com.omnicrola.voxel.data.GameXmlDataParser;
import com.omnicrola.voxel.data.read.FileReaderStrategyFactory;
import com.omnicrola.voxel.data.read.IFileReaderStrategy;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Eric on 1/18/2016.
 */
public class EntityDefinitionXmlAssetLoader implements AssetLoader {

    private final GameXmlDataParser definitionLoader;

    public EntityDefinitionXmlAssetLoader() {
        IFileReaderStrategy fileReaderStrategy = FileReaderStrategyFactory.build();
        this.definitionLoader = new GameXmlDataParser(fileReaderStrategy);
    }

    @Override
    public Object load(AssetInfo assetInfo) throws IOException {
        InputStream inputStream = assetInfo.openStream();
        UnitDefinitionRepository definitionLibrary = this.definitionLoader
                .loadDefinitions(inputStream);
        return definitionLibrary;
    }

}
