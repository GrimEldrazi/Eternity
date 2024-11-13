package net.grimm.eternity.test;

import net.grimm.eternity.common.network.IPacket;
import net.grimm.eternity.common.util.BufHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public class TestVariable extends SavedData {

    public static TestVariable clientside = new TestVariable();

    public int value = 0;

    public int getValue() {
        return value;
    }

    public void tick() {
        this.value++;
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        tag.putInt("value", value);
        return tag;
    }

    public static TestVariable load(CompoundTag tag, HolderLookup.Provider provider) {
        TestVariable data = new TestVariable();
        data.value = tag.getInt("value");
        return data;
    }

    public void sync(LevelAccessor level) {
        this.setDirty();
        if (level instanceof Level lvl && !lvl.isClientSide) {
            PacketDistributor.sendToAllPlayers(new TestPacket(this));
        }
    }

    public static TestVariable get(LevelAccessor level) {
        if (level instanceof ServerLevelAccessor accessor) {
            return accessor.getLevel().getServer().overworld().getDataStorage().computeIfAbsent(new Factory<>(TestVariable::new, TestVariable::load), "test_data");
        } else return clientside;
    }

}
