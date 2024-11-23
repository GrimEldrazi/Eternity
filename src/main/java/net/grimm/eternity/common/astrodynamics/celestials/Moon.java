package net.grimm.eternity.common.astrodynamics.celestials;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.grimm.eternity.Eternity;
import net.grimm.eternity.common.astrodynamics.orbits.OrbitTEST;
import net.grimm.eternity.common.astrodynamics.orbits.OrbitFactory;
import net.grimm.eternity.common.util.JsonManager;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Moon extends Celestial {

    public static final Codec<Moon> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.optionalFieldOf("namespace", Eternity.MOD_ID).forGetter(Moon::getNamespace),
            Codec.STRING.fieldOf("name").forGetter(Moon::getName),
            Codec.list(Codec.INT).fieldOf("colorID").forGetter(Moon::getRawColorID),
            Codec.DOUBLE.fieldOf("mass").forGetter(Moon::getMass),
            Codec.DOUBLE.fieldOf("radius").forGetter(Moon::getRadius),
            PlanetaryElements.CODEC.fieldOf("elements").forGetter(Moon::getElements),
            OrbitFactory.CODEC.fieldOf("orbit").forGetter(Moon::factory)
    ).apply(instance, instance.stable(Moon::new)));

    public static final JsonManager<Moon> MANAGER = new JsonManager<>("celestials/moons", CODEC, moon -> Celestial.CELESTIALS.put(moon.getName(), moon));

    public Moon(String namespace, String name, List<Integer> colorID, double mass, double radius, PlanetaryElements elements, OrbitFactory factory) {
        super(namespace, name, colorID, mass, radius, elements, factory);
    }

    @Override
    public OrbitTEST getOrbit() {
        return factory().createMoonOrbit();
    }

    public CompoundTag encode() {
        CompoundTag tag = new CompoundTag();
        tag.putString("namespace", getNamespace());
        tag.putString("name", getName());
        tag.putIntArray("color_id", getRawColorID());
        tag.putDouble("mass", getMass());
        tag.putDouble("radius", getRadius());
        getElements().encode(tag);
        factory().encode(tag);
        return tag;
    }

    public static Moon decode(CompoundTag tag) {
        return new Moon(
                tag.getString("namespace"),
                tag.getString("name"),
                new ArrayList<>(Arrays.asList(tag.getIntArray("color_id")[0], tag.getIntArray("color_id")[1], tag.getIntArray("color_id")[2])),
                tag.getDouble("mass"),
                tag.getDouble("radius"),
                PlanetaryElements.decode(tag),
                OrbitFactory.decode(tag)
        );
    }

}
