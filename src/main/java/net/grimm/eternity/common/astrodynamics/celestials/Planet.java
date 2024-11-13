package net.grimm.eternity.common.astrodynamics.celestials;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.grimm.eternity.common.astrodynamics.orbits.Orbit;
import net.grimm.eternity.common.astrodynamics.orbits.OrbitFactory;
import net.grimm.eternity.common.util.JsonManager;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Planet extends Celestial {

    public static final Codec<Planet> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(Planet::getName),
            Codec.list(Codec.INT).fieldOf("colorID").forGetter(Planet::getRawColorID),
            Codec.DOUBLE.fieldOf("mass").forGetter(Planet::getMass),
            Codec.DOUBLE.fieldOf("radius").forGetter(Planet::getRadius),
            Codec.BOOL.fieldOf("hasRings").forGetter(Planet::hasRings),
            PlanetaryElements.CODEC.fieldOf("elements").forGetter(Planet::getElements)
    ).apply(instance, instance.stable(Planet::new)));

    public static final JsonManager<Planet> MANAGER = new JsonManager<>("celestials/planet", CODEC, planet -> Celestial.CELESTIALS.put(planet.getName(), planet));

    private final boolean rings;

    public Planet(String name, List<Integer> colorID, double mass, double radius, boolean rings, PlanetaryElements elements) {
        super(name, colorID, mass, radius, elements, OrbitFactory.CENTRAL_STATIC_ORBIT_FACTORY);
        this.rings = rings;
    }

    public boolean hasRings() {
        return rings;
    }

    @Override
    public Orbit getOrbit() {
        return factory().createCentralStaticOrbit();
    }

    public CompoundTag encode() {
        CompoundTag tag = new CompoundTag();
        tag.putString("name", getName());
        tag.putIntArray("color_id", getRawColorID());
        tag.putDouble("mass", getMass());
        tag.putDouble("radius", getRadius());
        tag.putBoolean("rings", hasRings());
        getElements().encode(tag);
        return tag;
    }

    public static Planet decode(CompoundTag tag) {
        return new Planet(
                tag.getString("name"),
                new ArrayList<>(Arrays.asList(tag.getIntArray("color_id")[0], tag.getIntArray("color_id")[1], tag.getIntArray("color_id")[2])),
                tag.getDouble("mass"),
                tag.getDouble("radius"),
                tag.getBoolean("rings"),
                PlanetaryElements.decode(tag)
        );
    }
}
