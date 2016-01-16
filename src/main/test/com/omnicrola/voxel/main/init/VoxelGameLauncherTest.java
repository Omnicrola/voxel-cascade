package com.omnicrola.voxel.main.init;

import com.omnicrola.util.TestTools;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by omnic on 1/15/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class VoxelGameLauncherTest extends TestTools {

    @Mock
    VoxelGameEngine mockGameEngine;

    @Test
    public void testLaunch() throws Exception {
        VoxelGameLauncher voxelGameLauncher = new VoxelGameLauncher(mockGameEngine);
        verify(mockGameEngine, never()).start();

        voxelGameLauncher.launch();
        verify(mockGameEngine).start();
    }
}