package com.omnicrola.voxel.debug;

import com.jme3.input.controls.ActionListener;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.engine.VoxelGameEngine;

import java.util.List;

/**
 * Created by Eric on 2/5/2016.
 */
public class DebugSceneGraphListener implements ActionListener {
    private VoxelGameEngine voxelGameEngine;

    public DebugSceneGraphListener(VoxelGameEngine voxelGameEngine) {
        this.voxelGameEngine = voxelGameEngine;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed) {
            Node rootNode = this.voxelGameEngine.getRootNode();
            recursiveTreePrint(rootNode, "");
        }
    }

    public static void recursiveTreePrint(Spatial spatial, String prefix) {
        System.out.println(prefix + spatial.getName() + " : " + spatial.getWorldTranslation());
        if (spatial instanceof Node) {
            List<Spatial> children = ((Node) spatial).getChildren();
            children.forEach(c -> recursiveTreePrint(c, prefix + "- "));
        }
    }

}