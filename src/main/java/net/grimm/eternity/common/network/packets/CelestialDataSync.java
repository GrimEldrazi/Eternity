package net.grimm.eternity.common.network.packets;

import net.grimm.eternity.common.astrodynamics.celestials.Celestial;
import net.grimm.eternity.common.network.IPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class CelestialDataSync implements IPacket<CelestialDataSync> {

    private final CompoundTag tag;

    public CelestialDataSync(CompoundTag tag) {
        this.tag = tag;
    }

    public CelestialDataSync(RegistryFriendlyByteBuf buf) {
        this.tag = util.readNbt(buf);
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeNbt(tag);
    }

    @Override
    public void handleClient(CelestialDataSync msg, IPayloadContext ctx) {
        Celestial.CELESTIALS.clear();
        Celestial.CELESTIALS.putAll(Celestial.decodeCelestialMap(msg.tag));
    }

}
