package net.grimm.eternity.common.capability;

import net.grimm.eternity.common.network.IPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.function.Supplier;

public abstract class EntityCapabilityProvider<T extends INBTSerializable<CompoundTag>> {

    private final T data;
    private final LivingEntity entity;

    public EntityCapabilityProvider(LivingEntity entity) {
        this.data = entity.getData(typeSupplier());
        this.entity = entity;
    }

    public T getData() {
        return data;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public void sync() {
        if (entity instanceof ServerPlayer player) {
            CompoundTag tag = data.serializeNBT(player.registryAccess());
            PacketDistributor.sendToPlayer(player, packetRetriever(tag));
        }
    }

    protected abstract Supplier<AttachmentType<T>> typeSupplier();

    protected abstract IPacket<?> packetRetriever(CompoundTag tag);

}
