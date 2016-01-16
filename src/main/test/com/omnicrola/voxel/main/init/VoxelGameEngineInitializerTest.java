package com.omnicrola.voxel.main.init;

import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.omnicrola.util.TestTools;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by omnic on 1/15/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class VoxelGameEngineInitializerTest extends TestTools{

    @Mock
    AppStateManager mockStateManager;
    @Captor
    ArgumentCaptor<AppState> appStateCaptor;

    @Test
    public void testAttachesBulletAppState() throws Exception {

        doNothing().when(mockStateManager.attach(appStateCaptor.capture()));

        VoxelGameEngineInitializer.initializeGame(mockStateManager);

        assertIsOfType(BulletAppState.class, appStateCaptor.getValue());

    }
}