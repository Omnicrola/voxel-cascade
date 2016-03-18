package com.omnicrola.voxel.data.read;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Eric on 3/16/2016.
 */
public interface IFileReaderStrategy {
    List<String> getDirectoryContents(String directoryPath);

    InputStream getInputStream(String file);
}
