package com.omnicrola.voxel.data.read;

import sun.misc.Launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 3/16/2016.
 */
public class DevelopmentFileReaderStrategy implements IFileReaderStrategy {

    private static final Logger logger = Logger.getLogger(DevelopmentFileReaderStrategy.class.getName());

    @Override
    public List<File> getDirectoryContents(String directoryPath) {
        try {
            final URL url = Launcher.class.getResource("/" + directoryPath);
            if (url != null) {
                logger.log(Level.INFO, "Loading from IDE: " + url);
                final File apps = new File(url.toURI());
                List<File> files = Arrays.asList(apps.listFiles());
                return files;
            }
        } catch (URISyntaxException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return null;
    }

    @Override
    public InputStream getInputStream(File file) {
        try {
            logger.log(Level.FINE, "Loading filestream for : " + file.getName());
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return null;
    }
}
