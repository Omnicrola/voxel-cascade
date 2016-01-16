package com.omnicrola.voxel.main;

import com.omnicrola.voxel.main.init.Bootstrapper;
import com.omnicrola.voxel.main.init.VoxelGameLauncher;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by omnic on 1/10/2016.
 */
public class VoxelCascade  {

    public static void main(String[] args){
//        printClasspath();
        VoxelGameLauncher voxelGameLauncher = Bootstrapper.bootstrap();
        voxelGameLauncher.launch();
    }

    private static void printClasspath() {
        final ClassLoader systemClassLoader = ClassLoader
                .getSystemClassLoader();
        final URL[] urls = ((URLClassLoader) systemClassLoader).getURLs();
        for (final URL url : urls) {
            System.out.println("url: "+url.getFile());
        }
    }

}
