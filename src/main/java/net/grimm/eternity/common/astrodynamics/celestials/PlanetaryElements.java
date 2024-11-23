package net.grimm.eternity.common.astrodynamics.celestials;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;

public record PlanetaryElements(long rotPeriod, boolean tidalLock, float latitude,
                                float surfGrav, float airDens, boolean oxygen) {

    public static final Codec<PlanetaryElements> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.LONG.fieldOf("rotationPeriod").forGetter(PlanetaryElements::rotPeriod),
            Codec.BOOL.fieldOf("isTidalLocked").forGetter(PlanetaryElements::tidalLock),
            Codec.FLOAT.fieldOf("latitude").forGetter(PlanetaryElements::latitude),
            Codec.FLOAT.fieldOf("surfaceGravity").forGetter(PlanetaryElements::surfGrav),
            Codec.FLOAT.fieldOf("airDensity").forGetter(PlanetaryElements::airDens),
            Codec.BOOL.fieldOf("hasOxygen").forGetter(PlanetaryElements::oxygen)
    ).apply(instance, instance.stable(PlanetaryElements::new)));

    @Override
    public float latitude() {
        return (float) Math.toRadians(latitude);
    }

    public void encode(CompoundTag tag) {
        tag.putLong("rotPeriod", rotPeriod);
        tag.putBoolean("tidalLock", tidalLock);
        tag.putFloat("latitude", latitude);
        tag.putFloat("surfGrav", surfGrav);
        tag.putFloat("airDens", airDens);
        tag.putBoolean("oxygen", oxygen);
    }

    public static PlanetaryElements decode(CompoundTag tag) {
        return new PlanetaryElements(
                tag.getLong("rotPeriod"),
                tag.getBoolean("tidalLock"),
                tag.getFloat("latitude"),
                tag.getFloat("surfGrav"),
                tag.getFloat("airDens"),
                tag.getBoolean("oxygen")
        );
    }
}
