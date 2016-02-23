package com.omnicrola.voxel.engine;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by Eric on 2/22/2016.
 */
public interface IActionQueue {
    public <V> Future<V> enqueue(Callable<V> callable);
}
