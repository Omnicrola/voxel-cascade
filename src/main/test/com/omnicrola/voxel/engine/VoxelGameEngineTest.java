package com.omnicrola.voxel.engine;

import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.main.init.VoxelGameEngineInitializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * Created by omnic on 1/15/2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(VoxelGameEngineInitializer.class)
public class VoxelGameEngineTest {


    @Test
    public void testInitialize() throws Exception {
        mockStatic(VoxelGameEngineInitializer.class);

        ArgumentCaptor<AppStateManager> captor = ArgumentCaptor.forClass(AppStateManager.class);

        VoxelGameEngine voxelGameEngine = new VoxelGameEngine();
        voxelGameEngine.simpleInitApp();

        verifyStatic();
        VoxelGameEngineInitializer.initializeGame(captor.capture());
        assertSame(voxelGameEngine.getStateManager(), captor.getValue());

    }
}