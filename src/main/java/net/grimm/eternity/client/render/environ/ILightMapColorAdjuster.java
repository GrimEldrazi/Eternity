package net.grimm.eternity.client.render.environ;

import net.minecraft.client.multiplayer.ClientLevel;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public interface ILightMapColorAdjuster {

    void adjust(@NotNull ClientLevel level, float partialTicks, float skyDarken, float blockLightRedFlicker, float skyLight, int pixelX, int pixelY, @NotNull Vector3f colors);

}
