package com.omnicrola.voxel.util;

/**
 * Created by omnic on 1/16/2016.
 */
public class Tuple<L,R> {
    private L left;
    private R right;

    public Tuple(L left, R right){
        this.left = left;
        this.right =right;
    }

    public L getLeft() {
        return this.left;
    }

    public R getRight() {
        return this.right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple<?, ?> tuple = (Tuple<?, ?>) o;

        if (!left.equals(tuple.left)) return false;
        return right.equals(tuple.right);

    }

    @Override
    public int hashCode() {
        int result = left.hashCode();
        result = 31 * result + right.hashCode();
        return result;
    }
}
