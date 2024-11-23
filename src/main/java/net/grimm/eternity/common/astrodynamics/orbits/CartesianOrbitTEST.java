package net.grimm.eternity.common.astrodynamics.orbits;

import net.grimm.eternity.common.astrodynamics.celestials.Celestial;
import org.joml.Vector3d;

public class CartesianOrbitTEST implements OrbitTEST {

    private Celestial centralBody;
    private Vector3d position;
    private Vector3d velocity;

    public CartesianOrbitTEST(Celestial centralBody, Vector3d position, Vector3d velocity) {
        this.centralBody = centralBody;
        this.position = position;
        this.velocity = velocity;
    }

    @Override
    public Celestial getCentralBody() {
        return centralBody;
    }

    @Override
    public double a() {
        return -mu() / (2 * energy());
    }

    @Override
    public double e() {
        return Math.sqrt(1 - Math.pow(angMomentum(), 2) / (a() * mu()));
    }

    @Override
    public double i() {
        return Math.acos(angMomentumVec().z() / angMomentum());
    }

    @Override
    public double raan() {
        return Math.atan2(angMomentumVec().x(), -angMomentumVec().y());
    }

    @Override
    public double omega() {
        return Math.atan2(position.z() / Math.sin(i()), position.x() * Math.cos(raan()) + position().y() * Math.sin(raan())) - v();
    }

    @Override
    public double v() {
        return Math.atan2(Math.sqrt(semiParameter() / mu()) * (position.dot(velocity)), semiParameter() - radius());
    }

    @Override
    public Vector3d position() {
        return position;
    }

    @Override
    public Vector3d velocity() {
        return velocity;
    }

    @Override
    public double radius() {
        return position.length();
    }

    @Override
    public double angMomentum() {
        return angMomentumVec().length();
    }

    @Override
    public OrbitType type() {
        return OrbitType.CARTESIAN;
    }

    public Vector3d angMomentumVec() {
        return position.cross(velocity);
    }

    public double speed() {
        return velocity.length();
    }

    public double energy() {
        return Math.pow(speed(), 2) / 2 - mu() / radius();
    }

    public void shift() {
        position = position.add(velocity);
    }

    public void accelerate(Vector3d deltaV) {
        velocity = velocity.add(deltaV);
    }

    public KeplerianOrbitTEST toKeplerian() {
        return new KeplerianOrbitTEST(centralBody, a(), e(), i(), raan(), omega(), v());
    }

}
