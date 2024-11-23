package net.grimm.eternity.common.world.saved_data;

import net.grimm.eternity.common.data.GlobalData;
import net.grimm.eternity.common.network.packets.EpochSyncMessage;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class Epoch extends GlobalData<Epoch, EpochSyncMessage> {

    private long epoch = 0;
    private int delta = 1;

    public long getEpoch() {
        return epoch;
    }

    public void setEpoch(long epoch) {
        this.epoch = epoch;
    }

    public int getDelta() {
        return delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public void tick() {
        this.epoch += delta;
    }

    @Override
    protected @NotNull Function<GlobalData<Epoch, EpochSyncMessage>, EpochSyncMessage> messageConstructor() {
        return data -> new EpochSyncMessage((Epoch) data);
    }

    @Override
    public @NotNull Epoch load(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        Epoch data = new Epoch();
        data.epoch = tag.getLong("epoch");
        data.delta = tag.getInt("delta");
        return data;
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        tag.putLong("epoch", epoch);
        tag.putInt("delta", delta);
        return tag;
    }

    public static Epoch getInstance(LevelAccessor level) {
        return (Epoch) GlobalData.get(level, Epoch::new);
    }

}
