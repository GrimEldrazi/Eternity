package net.grimm.eternity.common.astrodynamics.orbits;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.grimm.eternity.common.astrodynamics.celestials.Celestial;
import net.minecraft.nbt.CompoundTag;
import org.joml.Vector3d;

public record OrbitFactory(double a, double e, double i, double raan, double omega) {

    public static final Codec<OrbitFactory> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("semiMajorAxis").forGetter(OrbitFactory::a),
            Codec.DOUBLE.fieldOf("eccentricity").forGetter(OrbitFactory::e),
            Codec.DOUBLE.fieldOf("inclination").forGetter(OrbitFactory::i),
            Codec.DOUBLE.fieldOf("rightAscensionOfAscNode").forGetter(OrbitFactory::raan),
            Codec.DOUBLE.fieldOf("argOfPeriapsis").forGetter(OrbitFactory::omega)
    ).apply(instance, instance.stable(OrbitFactory::new)));

    public static final OrbitFactory CENTRAL_STATIC_ORBIT_FACTORY = new OrbitFactory(0, Double.NaN, Double.NaN, Double.NaN, Double.NaN);

    public KeplerianOrbit createMoonOrbit() {
        return new KeplerianOrbit(Celestial.CELESTIALS.getPlanet(), a, e, Math.toRadians(i), Math.toRadians(raan), Math.toRadians(omega), 0);
    }

    public Orbit createCentralStaticOrbit() {
        return new Orbit() {
            @Override
            public Celestial getCentralBody() {
                return null;
            }

            @Override
            public double a() {
                return 0;
            }

            @Override
            public double e() {
                return Double.NaN;
            }

            @Override
            public double i() {
                return Double.NaN;
            }

            @Override
            public double raan() {
                return Double.NaN;
            }

            @Override
            public double omega() {
                return Double.NaN;
            }

            @Override
            public double v() {
                return Double.NaN;
            }

            @Override
            public Vector3d position() {
                return new Vector3d(0);
            }

            @Override
            public Vector3d velocity() {
                return new Vector3d(0);
            }

            @Override
            public double radius() {
                return 0;
            }

            @Override
            public double angMomentum() {
                return 0;
            }

            @Override
            public OrbitType type() {
                return OrbitType.STATIC;
            }

            @Override
            public double orbitPeriod() {
                return Double.NaN;
            }
        };
    }

    public void encode(CompoundTag tag) {
        tag.putDouble("a", a);
        tag.putDouble("e", e);
        tag.putDouble("i", i);
        tag.putDouble("raan", raan);
        tag.putDouble("omega", omega);
    }

    public static OrbitFactory decode(CompoundTag tag) {
        return new OrbitFactory(
                tag.getDouble("a"),
                tag.getDouble("e"),
                tag.getDouble("i"),
                tag.getDouble("raan"),
                tag.getDouble("omega")
        );
    }

}
