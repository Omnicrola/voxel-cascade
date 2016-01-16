package com.omnicrola.voxel.main;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.omnicrola.voxel.main.init.Bootstrapper;
import com.omnicrola.voxel.main.init.VoxelGameLauncher;

/**
 * Created by omnic on 1/10/2016.
 */
public class VoxelCascade  {

    public static void main(String[] args){
        VoxelGameLauncher voxelGameLauncher = Bootstrapper.bootstrap();
        voxelGameLauncher.launch();
    }


}
