package net.grimm.eternity.common.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

public class DebugMode implements INBTSerializable<CompoundTag> {

    private boolean hud;
    private boolean map;
    private boolean spaceship;

    public boolean isHud() {
        return hud;
    }

    public void setHud(boolean hud) {
        this.hud = hud;
    }

    public boolean isMap() {
        return map;
    }

    public void setMap(boolean map) {
        this.map = map;
    }

    public boolean isSpaceship() {
        return spaceship;
    }

    public void setSpaceship(boolean spaceship) {
        this.spaceship = spaceship;
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("hud", hud);
        tag.putBoolean("map", map);
        tag.putBoolean("spaceship", spaceship);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag tag) {
        this.hud = tag.getBoolean("hud");
        this.map = tag.getBoolean("map");
        this.spaceship = tag.getBoolean("spaceship");
    }

}
