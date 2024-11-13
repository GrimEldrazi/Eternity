package net.grimm.eternity.common.astrodynamics.orbits;

import net.grimm.eternity.common.astrodynamics.celestials.Celestial;
import net.grimm.eternity.common.util.EMath;
import net.minecraft.util.Mth;
import org.joml.Vector3d;

import java.util.function.Function;

public class KeplerianOrbit implements Orbit {

    private Celestial centralBody;
    private double a;
    private double e;
    private double i;
    private double raan;
    private double omega;
    private double v;

    public KeplerianOrbit(Celestial centralBody, double a, double e, double i, double raan, double omega, double v) {
        this.centralBody = centralBody;
        this.a = a;
        this.e = e;
        this.i = i;
        this.raan = raan;
        this.omega = omega;
        this.v = v;
    }

    public KeplerianOrbit(KeplerianOrbit keplerianOrbit, double v) {
        this.centralBody = keplerianOrbit.centralBody;
        this.a = keplerianOrbit.a;
        this.e = keplerianOrbit.e;
        this.i = keplerianOrbit.i;
        this.raan = keplerianOrbit.raan;
        this.omega = keplerianOrbit.omega;
        this.v = v;
    }

    @Override
    public Celestial getCentralBody() {
        return centralBody;
    }

    @Override
    public double a() {
        return a;
    }

    @Override
    public double e() {
        return e;
    }

    @Override
    public double i() {
        return i;
    }

    @Override
    public double raan() {
        return raan;
    }

    @Override
    public double omega() {
        return omega;
    }

    @Override
    public double v() {
        return v;
    }

    @Override
    public Vector3d position() {
        return new Vector3d(
                radius() * (Math.cos(raan) * Math.cos(omega + v) - Math.sin(raan) * Math.sin(omega + v) * Math.cos(i)),
                radius() * (Math.sin(raan) * Math.cos(omega + v) + Math.cos(raan) * Math.sin(omega + v) * Math.cos(i)),
                radius() * (Math.sin(i) * Math.sin(omega + v))
        );
    }

    @Override
    public Vector3d velocity() {
        return new Vector3d(
                position().x() * angMomentum() * e / (radius() * semiParameter()) * Math.sin(v) - angMomentum() / radius() * (Math.cos(raan) * Math.sin(omega + v) + Math.sin(raan) * Math.cos(omega + v) * Math.cos(i)),
                position().y() * angMomentum() * e / (radius() * semiParameter()) * Math.sin(v) - angMomentum() / radius() * (Math.sin(raan) * Math.sin(omega + v) - Math.cos(raan) * Math.cos(omega + v) * Math.cos(i)),
                position().z() * angMomentum() * e / (radius() * semiParameter()) * Math.sin(v) + angMomentum() / radius() * Math.sin(i) * Math.cos(omega + v)
        );
    }

    @Override
    public double radius() {
        return semiParameter() / (1 + e * Math.cos(v));
    }

    @Override
    public double angMomentum() {
        return Math.sqrt(mu() * a * (1 - e * e));
    }

    @Override
    public OrbitType type() {
        return OrbitType.KEPLERIAN;
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
        double t = fEpoch - iEpoch;
        double m = (t * meanMotion() + meanAnom()) % Mth.TWO_PI;
        double e = EMath.newtonMethod(m, 3, x -> x - this.e * Math.sin(x) - m, x -> 1 - this.e * Math.cos(x));
        double b = this.e / (1 + Math.sqrt(1 - this.e * this.e));
        this.v = e + 2 * Math.atan((b * Math.sin(e)) / (1 - b * Math.cos(e)));
    }

    public void staticPropagation(long epoch) {
        propagate(0, epoch);
    }

    public CartesianOrbit toCartesian() {
        return new CartesianOrbit(centralBody, position(), velocity());
    }
}
