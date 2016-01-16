package com.omnicrola.voxel.engine;

import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.omnicrola.voxel.main.init.VoxelGameEngineInitializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertSame;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

/**
 * Created by omnic on 1/15/2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(VoxelGameEngineInitializer.class)
public class VoxelGameEngineTest {

    @Mock
    BulletAppState mockBulletState;

    @Test
    public void testInitialize() throws Exception {
        mockStatic(VoxelGameEngineInitializer.class);

        ArgumentCaptor<AppStateManager> captor = ArgumentCaptor.forClass(AppStateManager.class);

        VoxelGameEngine voxelGameEngine = new VoxelGameEngine(mockBulletState);
        voxelGameEngine.simpleInitApp();

        verifyStatic();
        VoxelGameEngineInitializer.initializeGame(captor.capture());
        assertSame(voxelGameEngine.getStateManager(), captor.getValue());

    }
}