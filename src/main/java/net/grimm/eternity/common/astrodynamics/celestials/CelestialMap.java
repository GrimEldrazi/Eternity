package net.grimm.eternity.common.astrodynamics.celestials;

import net.grimm.eternity.common.astrodynamics.orbits.OrbitFactory;
import net.minecraft.util.Mth;

import java.util.*;

public class CelestialMap extends HashMap<String, Celestial> {

    public static final Celestial PROXY = new Moon("proxy", new ArrayList<>(Arrays.asList(0, 0, 0)), 1, 1,
            new PlanetaryElements(1, false, 0, 1, 0, false), OrbitFactory.CENTRAL_STATIC_ORBIT_FACTORY);

    @Override
    public Celestial get(Object key) {
        return getOrDefault(key, PROXY);
    }

    @Override
    public Celestial put(String key, Celestial value) {
        return putIfAbsent(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ? extends Celestial> m) {
        m.forEach(this::put);
    }

    public Planet getPlanet() {
        final Planet[] planet = new Planet[1];
        forEach((key, value) -> {
            if (value instanceof Planet plt) planet[0] = plt;
        });
        return planet[0];
    }

    public CelestialMap getMoons() {
        CelestialMap moons = new CelestialMap();
        forEach((key, value) -> {
            if (value instanceof Moon moon) moons.put(key, moon);
        });
        return moons;
    }

    public List<Celestial> sortAsList() {
        List<Celestial> celestialList = new ArrayList<>(values());
        celestialList.sort((o1, o2) -> Mth.floor(o1.getOrbit().a() - o2.getOrbit().a()));
        return celestialList;
    }

}
