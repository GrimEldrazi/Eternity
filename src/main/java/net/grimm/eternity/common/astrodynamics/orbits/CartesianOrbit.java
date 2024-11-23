package net.grimm.eternity.common.astrodynamics.orbits;

import net.grimm.eternity.common.astrodynamics.celestials.Celestial;
import org.joml.Vector3d;

public class CartesianOrbit extends Orbit {

    private Vector3d position;
    private Vector3d velocity;

    public CartesianOrbit(Celestial centralBody, Vector3d position, Vector3d velocity) {
        super(centralBody);
        this.position = position;
        this.velocity = velocity;
    }

    @Override
    public double getA() {
        return -mu() / (2 * energy());
    }

    @Override
    public double getE() {
        return Math.sqrt(1 - Math.pow(angMomentum(), 2) / (getA() * mu()));
    }

    @Override
    public double getI() {
        return Math.acos(angMomentumVec().z() / angMomentum());
    }

    @Override
    public double getRAAN() {
        return Math.atan2(angMomentumVec().x(), -angMomentumVec().y());
    }

    @Override
    public double getOmega() {
        return Math.atan2(position.z() / Math.sin(getI()), position.x() * Math.cos(getRAAN()) + getPosition().y() * Math.sin(getRAAN())) - getV();
    }

    @Override
    public double getV() {
        return Math.atan2(Math.sqrt(semiParameter() / mu()) * (position.dot(velocity)), semiParameter() - radius());
    }

    @Override
    public Vector3d getPosition() {
        return position;
    }

    @Override
    public Vector3d getVelocity() {
        return velocity;
    }

    @Override
    public OrbitType type() {
        return OrbitType.CARTESIAN;
    }

    @Override
    protected double radius() {
        return position.length();
    }

    @Override
    protected double angMomentum() {
        return angMomentumVec().length();
    }

    private Vector3d angMomentumVec() {
        return position.cross(velocity);
    }

    private double speed() {
        return velocity.length();
    }

    private double energy() {
        return Math.pow(speed(), 2) / 2 - mu() / radius();
    }

    public KeplerianOrbit toKeplerian() {
        return new KeplerianOrbit(getCentralBody(), getA(), getE(), getI(), getRAAN(), getOmega(), getV());
    }

}
