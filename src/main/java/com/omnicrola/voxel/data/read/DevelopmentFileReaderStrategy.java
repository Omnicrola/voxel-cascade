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
import java.util.stream.Collectors;

/**
 * Created by Eric on 3/16/2016.
 */
public class DevelopmentFileReaderStrategy implements IFileReaderStrategy {

    private static final Logger logger = Logger.getLogger(DevelopmentFileReaderStrategy.class.getName());

    @Override
    public List<String> getDirectoryContents(String directoryPath) {
        try {
            final URL url = Launcher.class.getResource("/" + directoryPath);
            if (url != null) {
                final File apps = new File(url.toURI());
                List<String> files = Arrays.asList(apps.listFiles())
                        .stream()
                        .map(f -> f.getAbsoluteFile().getAbsolutePath())
                        .collect(Collectors.toList());
                return files;
            }
        } catch (URISyntaxException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return null;
    }

    @Override
    public InputStream getInputStream(String file) {
        try {
            logger.log(Level.FINE, "Loading filestream for : " + file);
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, file + " - " + e.getMessage());
        }
        return null;
    }
}
