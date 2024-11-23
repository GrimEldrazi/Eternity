package net.grimm.eternity.common.astrodynamics.orbits;

import net.grimm.eternity.common.astrodynamics.celestials.Celestial;
import net.minecraft.util.Mth;
import org.joml.Vector3d;

public abstract class Orbit {

    private static final double G = 6.674E-11;

    private final Celestial centralBody;

    protected Orbit(Celestial centralBody) {
        this.centralBody = centralBody;
    }

    public Celestial getCentralBody() {
        return centralBody;
    }

    protected double mu() {
        return getCentralBody().getMass() * G;
    }

    protected  double semiParameter() {
        return getA() * (1 - getE() * getE());
    }

    public double orbitPeriod() {
        return Mth.TWO_PI * Math.sqrt(Math.pow(getA(), 3) / mu());
    }

    public abstract double getA();

    public abstract double getE();

    public abstract double getI();

    public abstract double getRAAN();

    public abstract double getOmega();

    public abstract double getV();

    public abstract Vector3d getPosition();

    public abstract Vector3d getVelocity();

    public abstract OrbitType type();

    protected abstract double radius();

    protected abstract double angMomentum();

}
