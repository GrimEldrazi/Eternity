package net.grimm.eternity.client.render.environ;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.multiplayer.ClientLevel;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public interface ICloudRender {

    void render(@NotNull ClientLevel level, int ticks, float partialTick, @NotNull PoseStack poseStack, double camX, double camY, double camZ, @NotNull Matrix4f modelViewMatrix, @NotNull Matrix4f projectionMatrix);

}
