package net.grimm.eternity.client.render.environ.special_effects;


import net.grimm.eternity.Eternity;
import net.grimm.eternity.common.util.LevelUtil;

import java.util.HashSet;
import java.util.Set;

public final class DimensionEffects {

    public static final Set<CelestialSpecialEffects> EFFECTS = new HashSet<>();

    private static final CelestialSpecialEffects SPACE = new CelestialSpecialEffects(LevelUtil.SPACE_LOCATION);
    private static final CelestialSpecialEffects ETERNITY = new CelestialSpecialEffects(Eternity.prefix("eternity"));
    private static final CelestialSpecialEffects IRIDIUM = new CelestialSpecialEffects(Eternity.prefix("iridium"));

    static {
        EFFECTS.add(SPACE);
        EFFECTS.add(ETERNITY);
        EFFECTS.add(IRIDIUM);
    }

}
