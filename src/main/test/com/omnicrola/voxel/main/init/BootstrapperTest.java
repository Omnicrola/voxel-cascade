package com.omnicrola.voxel.main.init;

import com.omnicrola.util.TestTools;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import org.junit.Test;


/**
 * Created by omnic on 1/15/2016.
 */
public class BootstrapperTest extends TestTools {
    @Test
    public void testBootstrap() throws Exception {
        VoxelGameLauncher voxelGameLauncher = assertIsOfType(VoxelGameLauncher.class, Bootstrapper.bootstrap());
        verifyDependency(VoxelGameEngine.class, "GameEngine", voxelGameLauncher);
    }


}