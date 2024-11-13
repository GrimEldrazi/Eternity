package net.grimm.eternity.common.network;

import net.grimm.eternity.Eternity;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public interface IPacket<T extends IPacket<T>> extends CustomPacketPayload {

    void toBytes(RegistryFriendlyByteBuf buf);

    default void handleClient(T msg, IPayloadContext ctx) {

    }

    default void handleServer(T msg, IPayloadContext ctx) {

    }

    default Type<T> createType() {
        return new Type<>(Eternity.prefix("eternity_packet_" + this.getClass().getName().toLowerCase()));
    }

    default StreamCodec<RegistryFriendlyByteBuf, T> streamCodec(Function<RegistryFriendlyByteBuf, T> constructor) {
        return StreamCodec.ofMember(T::toBytes, constructor::apply);
    }

    @Override
    default @NotNull Type<? extends CustomPacketPayload> type() {
        return createType();
    }

}
