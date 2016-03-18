package com.omnicrola.voxel.main;

import com.omnicrola.voxel.data.read.ProductionFileReaderStrategy;
import com.omnicrola.voxel.main.init.Bootstrapper;
import com.omnicrola.voxel.main.init.VoxelGameLauncher;
import com.omnicrola.voxel.settings.GameConstants;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by omnic on 1/10/2016.
 */
public class VoxelCascade {

    private static Logger LOGGER;

    public static void main(String[] args) {
        try {
            startLogging();
            printClasspath();


            VoxelGameLauncher voxelGameLauncher = Bootstrapper.bootstrap();
            voxelGameLauncher.launch();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception during startup: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void startLogging() {
        Logger baselogger = Logger.getLogger("");
        Level logLevel = getLogLevel();
        baselogger.setLevel(logLevel);
        FileHandler fh;

        try {
            fh = new FileHandler("voxel-cascade.log");
            baselogger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER = Logger.getLogger(VoxelCascade.class.getName());
    }

    private static Level getLogLevel() {
        String property = System.getProperty(GameConstants.PROPERTY_LOG_LEVEL, "WARNING");
        return Level.parse(property);
    }

    private static void printClasspath() {
        final ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        final URL[] urls = ((URLClassLoader) systemClassLoader).getURLs();
        LOGGER.log(Level.FINE, "Dumping classpath");
        for (final URL url : urls) {
            LOGGER.log(Level.FINE, "  Classpath: " + url.getFile());
        }
    }
}
