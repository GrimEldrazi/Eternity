package net.grimm.eternity.common.network.packets;

import net.grimm.eternity.common.data.GlobalData;
import net.grimm.eternity.common.network.SyncMessage;
import net.grimm.eternity.common.util.BufHelper;
import net.grimm.eternity.common.world.saved_data.Epoch;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class EpochSyncMessage extends SyncMessage<Epoch, EpochSyncMessage> {

    public EpochSyncMessage(Epoch data) {
        super(data);
    }

    public EpochSyncMessage(RegistryFriendlyByteBuf buf) {
        super(GlobalData.decode(BufHelper.readNbt(buf), Epoch::new));
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeNbt(data.encode());
    }

    @Override
    public void handleClient(EpochSyncMessage msg, IPayloadContext ctx) {
        msg.data.handle(msg, ctx);
    }
}
