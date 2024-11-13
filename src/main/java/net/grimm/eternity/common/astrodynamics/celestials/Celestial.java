package net.grimm.eternity.common.astrodynamics.celestials;

import net.grimm.eternity.common.astrodynamics.orbits.Orbit;
import net.grimm.eternity.common.astrodynamics.orbits.OrbitFactory;
import net.grimm.eternity.common.util.EColor;
import net.minecraft.nbt.CompoundTag;

import java.util.List;

public abstract class Celestial {

    public static final CelestialMap CELESTIALS = new CelestialMap();

    private final String name;
    private final List<Integer> colorID;
    private final double mass;
    private final double radius;
    private final PlanetaryElements elements;
    private final OrbitFactory factory;

    public Celestial(String name, List<Integer> colorID, double mass, double radius, PlanetaryElements elements, OrbitFactory factory) {
        this.name = name;
        this.colorID = colorID;
        this.mass = mass;
        this.radius = radius;
        this.elements = elements;
        this.factory = factory;
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

    public double getRadius() {
        return radius;
    }

    public PlanetaryElements getElements() {
        return elements;
    }

    protected OrbitFactory factory() {
        return factory;
    }

    public List<Integer> getRawColorID() {
        return colorID;
    }

    public boolean isPlanet() {
        return this instanceof Planet;
    }

    public abstract Orbit getOrbit();

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
