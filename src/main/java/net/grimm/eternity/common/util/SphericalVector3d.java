package net.grimm.eternity.common.util;

import org.joml.Vector3d;

public class SphericalVector3d {

    public double radius;
    public double polarAng;
    public double azimuthAng;

    public SphericalVector3d(double radius, double polarAng, double azimuthAng) {
        this.radius = radius;
        this.polarAng = polarAng;
        this.azimuthAng = azimuthAng;
    }

    public SphericalVector3d(Vector3d position) {
        this.radius = position.length();
        this.polarAng = Math.acos(position.z() / position.length());
        this.azimuthAng = Math.atan2(position.y(), position.x());
    }

    public SphericalVector3d add(double radius, double polarAng, double azimuthAng) {
        return new SphericalVector3d(this.radius + radius, this.polarAng + polarAng, this.azimuthAng + azimuthAng);
    }

    public Vector3d toCartesian() {
        return new Vector3d(x(), y(), z());
    }

    @Override
    public String toString() {
        return new Vector3d(radius, polarAng, azimuthAng).toString();
    }

    private double x() {
        return radius * Math.sin(polarAng) * Math.cos(azimuthAng);
    }

    private double y() {
        return radius * Math.sin(polarAng) * Math.sin(azimuthAng);
    }

    private double z() {
        return radius * Math.cos(polarAng);
    }

}
