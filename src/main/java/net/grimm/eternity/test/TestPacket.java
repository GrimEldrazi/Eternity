package net.grimm.eternity.test;

import net.grimm.eternity.common.network.IPacket;
import net.grimm.eternity.common.util.BufHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class TestPacket implements IPacket<TestPacket> {

    private final TestVariable data;

    public TestPacket(TestVariable data) {
        this.data = data;
    }

    public TestPacket(RegistryFriendlyByteBuf buf) {
        TestVariable data = new TestVariable();
        data.value = BufHelper.readNbt(buf).getInt("value");
        this.data = data;
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("value", data.value);
        buf.writeNbt(tag);
    }

    @Override
    public void handleClient(TestPacket msg, IPayloadContext ctx) {
        TestVariable.clientside = msg.data;
    }
}
