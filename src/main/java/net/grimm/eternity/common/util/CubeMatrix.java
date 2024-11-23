package net.grimm.eternity.common.util;

import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class CubeMatrix {

    private final double[][] nodes;

    public CubeMatrix() {
        this.nodes = new double[][]{{-1, -1, -1}, {-1, -1, 1}, {-1, 1, -1}, {-1, 1, 1},
                {1, -1, -1}, {1, -1, 1}, {1, 1, -1}, {1, 1, 1}};

    }

    public double[][] getMatrix() {
        return nodes;
    }

    public void scale(double x, double y, double z) {
        for (double[] node : nodes) {
            node[0] *= x;
            node[1] *= y;
            node[2] *= z;
        }
    }

    public void scale(double scalar) {
        scale(scalar, scalar, scalar);
    }

    public void translate(double x, double y, double z) {
        for (double[] node : nodes) {
            node[0] += x;
            node[1] += y;
            node[2] += z;
        }
    }

    public void translate(Vector3d vector) {
        translate(vector.x(), vector.y(), vector.z());
    }

    public void rotate(double yaw, double pitch) {
        double sinX = Math.sin(yaw);
        double cosX = Math.cos(yaw);
        double sinY = Math.sin(pitch);
        double cosY = Math.cos(pitch);
        for (double[] node : nodes) {
            double x = node[0];
            double y = node[1];
            double z = node[2];
            node[0] = x * cosX - z * sinX;
            node[2] = z * cosX + x * sinX;
            z = node[2];
            node[1] = y * cosY - z * sinY;
            node[2] = z * cosY + y * sinY;
        }
    }

    public float[][] toMatrixF() {
        float[][] matrixF = new float[8][3];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 3; j++) {
                matrixF[i][j] = (float) nodes[i][j];
            }
        }
        return matrixF;
    }

    public record PlanarNodes(float[][] nodes, int plane) {

        public Vector3f node0() {
            return switch (plane) {
                case 0 -> convert(nodes[0]);
                case 1 -> convert(nodes[4]);
                case 2 -> convert(nodes[5]);
                case 3, 5 -> convert(nodes[1]);
                case 4 -> convert(nodes[2]);
                default -> new Vector3f(0);
            };
        }

        public Vector3f node1() {
            return switch (plane) {
                case 0 -> convert(nodes[4]);
                case 1, 5 -> convert(nodes[5]);
                case 2 -> convert(nodes[1]);
                case 3 -> convert(nodes[0]);
                case 4 -> convert(nodes[6]);
                default -> new Vector3f(0);
            };
        }

        public Vector3f node2() {
            return switch (plane) {
                case 0 -> convert(nodes[6]);
                case 1, 4 -> convert(nodes[7]);
                case 2 -> convert(nodes[3]);
                case 3 -> convert(nodes[2]);
                case 5 -> convert(nodes[4]);
                default -> new Vector3f(0);
            };
        }

        public Vector3f node3() {
            return switch (plane) {
                case 0 -> convert(nodes[2]);
                case 1 -> convert(nodes[6]);
                case 2 -> convert(nodes[7]);
                case 3, 4 -> convert(nodes[3]);
                case 5 -> convert(nodes[0]);
                default -> new Vector3f(0);
            };
        }

        private Vector3f convert(float[] array) {
            return new Vec3(array[0], array[1], array[2]).toVector3f();
        }

    }

}
