package com.omnicrola.voxel.terrain;

import java.util.Random;

/**
 * Created by Eric on 2/3/2016.
 */

public class PerlinNoiseGenerator {
    private Random random;
    private int octaveCount;
    private float amplitude;

    public PerlinNoiseGenerator() {
        this.random = new Random();
        this.amplitude = 1.0f;
        this.octaveCount = 4;
    }

    public float[][] generate(int width, int height, int seed) {
        this.random = new Random(seed);
        final float[][] whiteNoise = generateWhiteNoise(width, height);
        return generatePerlinNoise(whiteNoise, this.octaveCount);
    }

    private float[][] generatePerlinNoise(float[][] baseNoise, int octaveCount) {
        final int width = baseNoise.length;
        final int height = baseNoise[0].length;

        final float[][][] smoothNoise = new float[octaveCount][][]; // an array
        // of 2D
        // arrays
        // containing

        final float persistance = 0.5f;

        // generate smooth noise
        for (int i = 0; i < octaveCount; i++) {
            smoothNoise[i] = generateSmoothNoise(baseNoise, i);
        }

        final float[][] perlinNoise = new float[width][height];
        this.amplitude = 1.0f;
        float totalAmplitude = 0.0f;

        // blend noise together
        for (int octave = octaveCount - 1; octave >= 0; octave--) {
            this.amplitude *= persistance;
            totalAmplitude += this.amplitude;

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    perlinNoise[i][j] += smoothNoise[octave][i][j]
                            * this.amplitude;
                }
            }
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                perlinNoise[i][j] /= totalAmplitude;
                perlinNoise[i][j] = (float) (Math.floor(perlinNoise[i][j] * 25));
            }
        }

        return perlinNoise;
    }

    private float[][] generateSmoothNoise(float[][] baseNoise, int octave) {
        final int width = baseNoise.length;
        final int height = baseNoise[0].length;

        final float[][] smoothNoise = new float[width][height];

        final int samplePeriod = 1 << octave; // calculates 2 ^ k
        final float sampleFrequency = 1.0f / samplePeriod;

        for (int i = 0; i < width; i++) {
            // calculate the horizontal sampling indices
            final int sample_i0 = (i / samplePeriod) * samplePeriod;
            final int sample_i1 = (sample_i0 + samplePeriod) % width; // wrap
            // around
            final float horizontal_blend = (i - sample_i0) * sampleFrequency;

            for (int j = 0; j < height; j++) {
                // calculate the vertical sampling indices
                final int sample_j0 = (j / samplePeriod) * samplePeriod;
                final int sample_j1 = (sample_j0 + samplePeriod) % height; // wrap
                // around
                final float vertical_blend = (j - sample_j0) * sampleFrequency;

                // blend the top two corners
                final float top = interpolate(baseNoise[sample_i0][sample_j0],
                        baseNoise[sample_i1][sample_j0], horizontal_blend);

                // blend the bottom two corners
                final float bottom = interpolate(
                        baseNoise[sample_i0][sample_j1],
                        baseNoise[sample_i1][sample_j1], horizontal_blend);

                // final blend
                smoothNoise[i][j] = interpolate(top, bottom, vertical_blend);
            }
        }

        return smoothNoise;
    }

    private float[][] generateWhiteNoise(int width, int height) {
        final float[][] noise = new float[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                noise[i][j] = (float) this.random.nextDouble() % 1;
            }
        }

        return noise;
    }

    private float interpolate(float x0, float x1, float alpha) {
        return x0 * (1 - alpha) + alpha * x1;
    }

    public void setAmplitude(float amplitude) {
        this.amplitude = amplitude;
    }

    public void setOctaves(int octaveCount) {
        this.octaveCount = octaveCount;
    }
}
