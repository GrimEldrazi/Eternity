package net.grimm.eternity.common.astrodynamics.orbits;

import net.grimm.eternity.common.astrodynamics.celestials.Celestial;
import net.grimm.eternity.common.util.EMath;
import net.minecraft.util.Mth;
import org.joml.Vector3d;

public class KeplerianOrbit extends Orbit {

    private final double a;
    private final double e;
    private final double i;
    private final double raan;
    private final double omega;
    private double v;

    public KeplerianOrbit(Celestial centralBody, double a, double e, double i, double raan, double omega, double v) {
        super(centralBody);
        this.a = a;
        this.e = e;
        this.i = i;
        this.raan = raan;
        this.omega = omega;
        this.v = v;
    }

    @Override
    public double getA() {
        return a;
    }

    @Override
    public double getE() {
        return e;
    }

    @Override
    public double getI() {
        return i;
    }

    @Override
    public double getRAAN() {
        return raan;
    }

    @Override
    public double getOmega() {
        return omega;
    }

    @Override
    public double getV() {
        return v;
    }

    @Override
    public Vector3d getPosition() {
        return new Vector3d(
                radius() * (Math.cos(raan) * Math.cos(omega + v) - Math.sin(raan) * Math.sin(omega + v) * Math.cos(i)),
                radius() * (Math.sin(raan) * Math.cos(omega + v) + Math.cos(raan) * Math.sin(omega + v) * Math.cos(i)),
                radius() * (Math.sin(i) * Math.sin(omega + v))
        );
    }

    @Override
    public Vector3d getVelocity() {
        return new Vector3d(
                getPosition().x() * angMomentum() * e / (radius() * semiParameter()) * Math.sin(v) - angMomentum() / radius() * (Math.cos(raan) * Math.sin(omega + v) + Math.sin(raan) * Math.cos(omega + v) * Math.cos(i)),
                getPosition().y() * angMomentum() * e / (radius() * semiParameter()) * Math.sin(v) - angMomentum() / radius() * (Math.sin(raan) * Math.sin(omega + v) - Math.cos(raan) * Math.cos(omega + v) * Math.cos(i)),
                getPosition().z() * angMomentum() * e / (radius() * semiParameter()) * Math.sin(v) + angMomentum() / radius() * Math.sin(i) * Math.cos(omega + v)
        );
    }

    @Override
    public OrbitType type() {
        return OrbitType.KEPLERIAN;
    }

    @Override
    protected double radius() {
        return semiParameter() / (1 + e * Math.cos(v));
    }

    @Override
    protected double angMomentum() {
        return Math.sqrt(mu() * a * (1 - e * e));
    }

    public double meanAnom() {
        return eccentricAnom() - e * Math.sin(eccentricAnom());
    }

    public double meanMotion() {
        return Math.sqrt(mu() / Math.abs(a * a * a));
    }

    public double eccentricAnom() {
        return Math.atan((Math.sin(v) * Math.sqrt(1 - e * e)) / (Math.cos(v) + e));
    }

    public void propagate(long iEpoch, long fEpoch) {
        double t = -fEpoch + iEpoch;
        double m = (t * meanMotion() + meanAnom()) % Mth.TWO_PI;
        double e = EMath.newtonMethod(m, 3, x -> x - this.e * Math.sin(x) - m, x -> 1 - this.e * Math.cos(x));
        double b = this.e / (1 + Math.sqrt(1 - this.e * this.e));
        this.v = e + 2 * Math.atan((b * Math.sin(e)) / (1 - b * Math.cos(e)));
    }

    public void staticPropagation(long epoch) {
        propagate(0, epoch);
    }

    public CartesianOrbit toCartesian() {
        return new CartesianOrbit(getCentralBody(), getPosition(), getVelocity());
    }

}
