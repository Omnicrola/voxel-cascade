package com.omnicrola.voxel.main;

import com.omnicrola.voxel.main.init.ServerBootstrapper;
import com.omnicrola.voxel.main.init.VoxelServerLauncher;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by Eric on 2/21/2016.
 */
public class VoxelCascadeServer {

    public static void main(String[] args) {
//        printClasspath();
        VoxelServerLauncher voxelGameLauncher = ServerBootstrapper.bootstrap();
        voxelGameLauncher.launch();
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
