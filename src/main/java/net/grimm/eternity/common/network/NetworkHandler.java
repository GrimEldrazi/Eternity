package net.grimm.eternity.common.network;

import net.grimm.eternity.common.network.packets.CelestialDataSync;
import net.grimm.eternity.common.network.packets.EpochSyncMessage;
import net.grimm.eternity.common.network.packets.UpdateDebugPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import java.util.function.Function;

public class NetworkHandler {

    private final PayloadRegistrar reg;

    private NetworkHandler(PayloadRegistrar reg) {
        this.reg = reg;
    }

    public static void register(RegisterPayloadHandlersEvent event) {
        NetworkHandler net = new NetworkHandler(event.registrar("1"));

        //Server to Client
        net.playToClient(CelestialDataSync::new);
        net.playToClient(EpochSyncMessage::new);
        net.playToClient(UpdateDebugPacket::new);

    }

    private <T extends IPacket<T>> void playToClient(Function<RegistryFriendlyByteBuf, T> function) {
        reg.playToClient(function.apply(null).createType(), function.apply(null).streamCodec(function), NetworkHandler::handle);
    }

    private <T extends IPacket<T>> void playToServer(Function<RegistryFriendlyByteBuf, T> function) {
        reg.playToServer(function.apply(null).createType(), function.apply(null).streamCodec(function), NetworkHandler::handle);
    }

    private static <T extends IPacket<T>> void handle(T msg, IPayloadContext ctx) {
        if (ctx.flow().getReceptionSide() == LogicalSide.SERVER) {
            msg.handleServer(msg, ctx);
        } else msg.handleClient(msg, ctx);
    }

}
