package net.grimm.eternity.common.util;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.grimm.eternity.Eternity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Consumer;

public class JsonManager<T> extends SimpleJsonResourceReloadListener {

    private final Codec<T> codec;
    private final Consumer<T> consumer;

    public JsonManager(String directory, Codec<T> codec, Consumer<T> consumer) {
        super(Eternity.GSON, directory);
        this.codec = codec;
        this.consumer = consumer;
    }

    @Override
    public void apply(@NotNull Map<ResourceLocation, JsonElement> elementMap, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller) {
        elementMap.forEach((location, element) -> {
            try {
                codec.parse(JsonOps.INSTANCE, element).resultOrPartial().ifPresent(consumer);
            } catch (Exception e) {
                Eternity.LOGGER.error("Eternity Error: file parsing failed for: {}", location, e);
            }
        });
    }
}
