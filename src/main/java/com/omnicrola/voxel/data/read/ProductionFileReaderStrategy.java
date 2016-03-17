package com.omnicrola.voxel.data.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 3/16/2016.
 */
public class ProductionFileReaderStrategy implements IFileReaderStrategy {

    private static final Logger logger = Logger.getLogger(ProductionFileReaderStrategy.class.getName());

    private final JarFile jar;

    public ProductionFileReaderStrategy(JarFile jar) {
        this.jar = jar;
    }

    @Override
    public List<File> getDirectoryContents(String directoryPath) {
        logger.log(Level.INFO, "Loading from JAR");
        try {
            final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
            ArrayList<File> files = new ArrayList<>();
            while (entries.hasMoreElements()) {
                final String name = entries.nextElement().getName();
                if (name.startsWith(directoryPath + "/")) { //filter according to the path
                    logger.log(Level.FINE, "Found : " + name);
                    files.add(new File(name));
                }
            }
            jar.close();
            return files;
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public FileInputStream getInputStream(File file) {
        return null;
    }
}
