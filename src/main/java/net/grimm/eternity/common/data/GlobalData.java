package net.grimm.eternity.common.data;

import net.grimm.eternity.common.network.SyncMessage;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class GlobalData<T extends GlobalData<T, U>, U extends SyncMessage<T, U>> extends SavedData {

    public static final Map<String, GlobalData<?, ?>> CLIENT_DATA = new HashMap<>();
    private static final HolderLookup.Provider PROVIDER = new HolderLookup.Provider() {
        @Override
        public @NotNull Stream<ResourceKey<? extends Registry<?>>> listRegistries() {
            return Stream.empty();
        }

        @Override
        public <V> @NotNull Optional<HolderLookup.RegistryLookup<V>> lookup(@NotNull ResourceKey<? extends Registry<? extends V>> resourceKey) {
            return Optional.empty();
        }
    };

    protected final String id = this.getClass().getName().toLowerCase();

    protected GlobalData() {
        CLIENT_DATA.putIfAbsent(id, this);
    }

    public void sync(LevelAccessor level) {
        setDirty();
        if (level instanceof Level lvl && !lvl.isClientSide) {
            PacketDistributor.sendToAllPlayers(messageConstructor().apply(this));
        }
    }

    public void handle(U msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            CLIENT_DATA.replace(msg.getData().id, msg.getData());
        });
    }

    public CompoundTag encode() {
        return save(new CompoundTag(), PROVIDER);
    }

    protected Factory<T> factory(Supplier<T> constructor) {
        return new Factory<>(constructor, constructor.get()::load);
    }

    public static <T extends GlobalData<T, ?>> GlobalData<?, ?> get(LevelAccessor level, Supplier<T> constructor) {
        T data = constructor.get();
        if (level instanceof ServerLevelAccessor accessor) {
            return accessor.getLevel().getServer().overworld().getDataStorage().computeIfAbsent(data.factory(constructor), data.id);
        } else return CLIENT_DATA.get(data.id);
    }

    public static <T extends GlobalData<T, ?>> T decode(CompoundTag tag, Supplier<T> constructor) {
        return constructor.get().load(tag, PROVIDER);
    }

    protected abstract @NotNull Function<GlobalData<T, U>, U> messageConstructor();

    protected abstract @NotNull T load(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider provider);

}
