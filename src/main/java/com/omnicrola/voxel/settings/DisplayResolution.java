package com.omnicrola.voxel.settings;

/**
 * Created by Eric on 4/3/2016.
 */
public class DisplayResolution implements Comparable<DisplayResolution> {
    private final int width;
    private final int height;

    public DisplayResolution(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DisplayResolution)) return false;

        DisplayResolution that = (DisplayResolution) o;

        if (width != that.width) return false;
        if (height != that.height) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        return result;
    }

    @Override
    public String toString() {
        return this.width + " x " + this.height;
    }

    @Override
    public int compareTo(DisplayResolution other) {
        int width = Integer.compare(this.width, other.width);
        if (width != 0) {
            return width;
        }
        return Integer.compare(this.height, other.height);

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
