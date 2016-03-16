package com.omnicrola.voxel.main;

import com.omnicrola.voxel.main.init.Bootstrapper;
import com.omnicrola.voxel.main.init.VoxelGameLauncher;

import java.io.IOException;
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

    private static final Logger logger = Logger.getLogger(VoxelCascade.class.getName());


    public static void main(String[] args) {
        try {
//        printClasspath();
            startLogging();
            VoxelGameLauncher voxelGameLauncher = Bootstrapper.bootstrap();
            voxelGameLauncher.launch();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception during startup: " + e.getMessage());
        }
    }

    private static void startLogging() {
        Logger logger = Logger.getLogger("");
        FileHandler fh;

        try {
            fh = new FileHandler("voxel-cascade.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printClasspath() {
        final ClassLoader systemClassLoader = ClassLoader
                .getSystemClassLoader();
        final URL[] urls = ((URLClassLoader) systemClassLoader).getURLs();
        for (final URL url : urls) {
            System.out.println("url: " + url.getFile());
        }
    }
}
