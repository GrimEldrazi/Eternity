package net.grimm.eternity.client.render.environ;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public interface ISkyRender {

    void render(@NotNull ClientLevel level, int ticks, float partialTick, @NotNull Matrix4f modelViewMatrix, @NotNull Camera camera, @NotNull Matrix4f projectionMatrix, boolean isFoggy, @NotNull Runnable setupFog);

}
