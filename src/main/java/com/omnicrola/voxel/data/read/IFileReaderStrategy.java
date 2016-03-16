package com.omnicrola.voxel.data.read;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created by Eric on 3/16/2016.
 */
public interface IFileReaderStrategy {
    List<File> getDirectoryContents(String directoryPath);

    FileInputStream getInputStream(File file);
}
