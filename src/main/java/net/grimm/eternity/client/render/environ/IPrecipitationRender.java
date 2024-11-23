package net.grimm.eternity.client.render.environ;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import org.jetbrains.annotations.NotNull;

public interface IPrecipitationRender {

    void render(@NotNull ClientLevel level, int ticks, float partialTick, @NotNull LightTexture lightTexture, double camX, double camY, double camZ);

}
