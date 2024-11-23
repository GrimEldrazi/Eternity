package net.grimm.eternity.common.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;

public final class BufUtil {

    public CompoundTag readNbt(RegistryFriendlyByteBuf buf) {
        if (buf != null) {
            return buf.readNbt();
        }
        return new CompoundTag();
    }

}
