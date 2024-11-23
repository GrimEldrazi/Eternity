package net.grimm.eternity.common.util;

import net.grimm.eternity.Eternity;
import net.grimm.eternity.common.astrodynamics.celestials.Celestial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;

public final class LevelUtil {

    public static final ResourceLocation SPACE_LOCATION = ResourceLocation.fromNamespaceAndPath(Eternity.MOD_ID, "space");

    public static Celestial currentLevelCelestial(LevelAccessor level) {
        Celestial[] celestialArray = {null};
        Celestial.CELESTIALS.asList().forEach(celestial -> {
            if (level.dimensionType().effectsLocation().equals(celestial.getDimension())) {
                celestialArray[0] = celestial;
            }
        });
        return celestialArray[0];
    }

    public static boolean isSpace(LevelAccessor level) {
        return level.dimensionType().effectsLocation().equals(SPACE_LOCATION);
    }

}
