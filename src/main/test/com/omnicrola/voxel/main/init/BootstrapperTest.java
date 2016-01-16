package com.omnicrola.voxel.main.init;

import com.omnicrola.util.TestTools;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.util.Dependency;
import junit.framework.AssertionFailedError;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;


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