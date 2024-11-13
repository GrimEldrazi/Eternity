package net.grimm.eternity.common.capability;

import net.grimm.eternity.common.data.DebugMode;
import net.grimm.eternity.common.network.IPacket;
import net.grimm.eternity.common.network.packets.UpdateDebugPacket;
import net.grimm.eternity.common.registry.DataAttachments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.function.Supplier;

public class DebugCap extends EntityCapabilityProvider<DebugMode> {

    public DebugCap(LivingEntity entity) {
        super(entity);
    }

    @Override
    protected Supplier<AttachmentType<DebugMode>> typeSupplier() {
        return DataAttachments.DEBUG_ATTACHMENT;
    }

    @Override
    protected IPacket<?> packetRetriever(CompoundTag tag) {
        return new UpdateDebugPacket(tag);
    }
}
