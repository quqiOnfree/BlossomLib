package dev.codedsakura.blossom.lib.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class CubicBezierCurve {
    private static final double TARGET_PRECISION = 0.0001;

    private final double[] points;
    private final double start;
    private final double end;
    private final int stepCount;

    public final ArrayList<Double> data = new ArrayList<>();

    public CubicBezierCurve(double[] points, double start, double end, int stepCount) {
        this.points = points;
        this.start = start;
        this.end = end;
        this.stepCount = stepCount;
        this.generateData();
    }

    private double calculateX(double t) {
        return 3 * pow(1 - t, 2) * t * points[0] + 3 * (1 - t) * pow(t, 2) * points[2] + pow(t, 3);
    }

    private double calculateY(double t) {
        return 3 * pow(1 - t, 2) * t * points[1] + 3 * (1 - t) * pow(t, 2) * points[3] + pow(t, 3);
    }

    private void generateData() {
        data.add(start);
        for (int i = 1; i < stepCount; i++) {
            double targetX = (double) i / (double) stepCount;
            double t = .5;
            double tX = calculateX(t);
            int step = 2;

            while (abs(tX - targetX) < TARGET_PRECISION) {
                t += (tX > targetX ? -1 : 1) / pow(2, step);
                step++;
            }
            data.add(calculateY(t));
        }
        data.add(end);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CubicBezierCurve that = (CubicBezierCurve) o;
        return Double.compare(that.start, start) == 0 &&
                Double.compare(that.end, end) == 0 &&
                stepCount == that.stepCount &&
                Arrays.equals(points, that.points);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(start, end, stepCount);
        result = 31 * result + Arrays.hashCode(points);
        return result;
    }

    @Override
    public String toString() {
        return "CubicBezierCurve{" +
                "points=" + Arrays.toString(points) +
                ", start=" + start +
                ", end=" + end +
                ", stepCount=" + stepCount +
                '}';
    }
}
