package net.grimm.eternity.common.astrodynamics.orbits;

import net.grimm.eternity.common.astrodynamics.celestials.Celestial;
import net.minecraft.util.Mth;
import org.joml.Vector3d;

public interface Orbit {

    double G = 6.674E-11;

    default double mu() {
        return getCentralBody().getMass() * G;
    }

    default double semiParameter() {
        return a() * (1 - e() * e());
    }

    default double orbitPeriod() {
        return Mth.TWO_PI * Math.sqrt(Math.pow(a(), 3) / mu());
    }

    default void propagateIfKeplerian(long epoch) {
        if (this instanceof KeplerianOrbit orbit) {
            orbit.staticPropagation(epoch);
        }
    }

    Celestial getCentralBody();

    double a();

    double e();

    double i();

    double raan();

    double omega();

    double v();

    Vector3d position();

    Vector3d velocity();

    double radius();

    double angMomentum();

    OrbitType type();

}
