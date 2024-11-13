package net.grimm.eternity.common.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;

public class BufHelper {

    public static CompoundTag readNbt(RegistryFriendlyByteBuf buf) {
        if (buf != null) {
            return buf.readNbt();
        } else return new CompoundTag();
    }

}
