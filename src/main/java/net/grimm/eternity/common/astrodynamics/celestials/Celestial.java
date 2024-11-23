package net.grimm.eternity.common.astrodynamics.celestials;

import net.grimm.eternity.common.astrodynamics.orbits.OrbitTEST;
import net.grimm.eternity.common.astrodynamics.orbits.OrbitFactory;
import net.grimm.eternity.common.astrodynamics.parallax.IParallaxProvider;
import net.grimm.eternity.common.util.EColor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Vector3d;

import java.util.List;

public abstract class Celestial implements IParallaxProvider {

    public static final CelestialMap CELESTIALS = new CelestialMap();
    public static final float SUN_SIZE = 10;

    private final String namespace;
    private final String name;
    private final List<Integer> colorID;
    private final double mass;
    private final double radius;
    private final PlanetaryElements elements;
    private final OrbitFactory factory;

    public Celestial(String namespace, String name, List<Integer> colorID, double mass, double radius, PlanetaryElements elements, OrbitFactory factory) {
        this.namespace = namespace;
        this.name = name;
        this.colorID = colorID;
        this.mass = mass;
        this.radius = radius;
        this.elements = elements;
        this.factory = factory;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getName() {
        return name;
    }

    public EColor getColorID() {
        return new EColor(colorID);
    }

    public double getMass() {
        return mass;
    }

    @Override
    public double getRadius() {
        return radius;
    }

    public PlanetaryElements getElements() {
        return elements;
    }

    @Override
    public Vector3d getPosition(long epoch) {
        OrbitTEST orbitTEST = getOrbit();
        orbitTEST.propagateIfKeplerian(epoch);
        return orbitTEST.position();
    }

    protected OrbitFactory factory() {
        return factory;
    }

    public List<Integer> getRawColorID() {
        return colorID;
    }

    public abstract OrbitTEST getOrbit();

    public boolean isPlanet() {
        return this instanceof Planet;
    }

    public ResourceLocation getDimension() {
        return ResourceLocation.fromNamespaceAndPath(namespace, name);
    }

    public ResourceLocation getTexture() {
        return ResourceLocation.fromNamespaceAndPath(namespace, "textures/sky/" + name + ".png");
    }

    public double spinProgress(long epoch) {
        double omegaOffset = Double.isNaN(factory().omega()) ? 0 : Math.toRadians(-factory().omega() + 180);
        return ((epoch % rotPeriod()) / rotPeriod()) * Mth.TWO_PI + omegaOffset;
    }

    public double rotPeriod() {
        return elements.tidalLock() ? getOrbit().orbitPeriod() : elements.rotPeriod();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Celestial celestial) {
            return name.equals(celestial.name);
        }
        return false;
    }

    public static CompoundTag encodeCelestialMap(CelestialMap celestials) {
        CompoundTag tag = new CompoundTag();
        CompoundTag planetTag = new CompoundTag();
        CompoundTag moonTag = new CompoundTag();
        celestials.forEach((key, value) -> {
            if (value instanceof Planet planet) {
                planetTag.put("key", planet.encode());
            } else if (value instanceof Moon moon) {
                moonTag.put(key, moon.encode());
            }
        });
        tag.put("planet", planetTag);
        tag.put("moon", moonTag);
        return tag;
    }

    public static CelestialMap decodeCelestialMap(CompoundTag tag) {
        CelestialMap celestials = new CelestialMap();
        CompoundTag planet = tag.getCompound("planet");
        CompoundTag moon = tag.getCompound("moon");
        planet.getAllKeys().forEach(key -> celestials.put(key, Planet.decode(planet.getCompound(key))));
        moon.getAllKeys().forEach(key -> celestials.put(key, Moon.decode(moon.getCompound(key))));
        return celestials;
    }

}
