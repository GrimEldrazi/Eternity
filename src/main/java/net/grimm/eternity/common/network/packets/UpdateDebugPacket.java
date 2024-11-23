package net.grimm.eternity.common.network.packets;

import net.grimm.eternity.common.data.DebugMode;
import net.grimm.eternity.common.network.IPacket;
import net.grimm.eternity.common.registry.DataAttachments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class UpdateDebugPacket implements IPacket<UpdateDebugPacket> {

    private final CompoundTag tag;

    public UpdateDebugPacket(CompoundTag tag) {
        this.tag = tag;
    }

    public UpdateDebugPacket(RegistryFriendlyByteBuf buf) {
        this.tag = util.readNbt(buf);
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeNbt(tag);
    }

    @Override
    public void handleClient(UpdateDebugPacket msg, IPayloadContext ctx) {
        Player player = ctx.player();
        DebugMode data = new DebugMode();
        data.deserializeNBT(player.registryAccess(), msg.tag);
        player.setData(DataAttachments.DEBUG_ATTACHMENT, data);
    }
}
