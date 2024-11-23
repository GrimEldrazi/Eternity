package net.grimm.eternity.common.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Axis;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public final class RenderUtil {

    public static final Tesselator TESSELATOR = Tesselator.getInstance();

    public static Matrix4f createSkyMatrix(PoseStack poseStack, double xRot, double yRot, double zRot) {
        return createMatrix(poseStack, xRot - Math.PI, yRot, -1 * zRot + Math.PI);
    }

    public static Matrix4f createMatrix(PoseStack poseStack, float xRot, float yRot, float zRot) {
        poseStack.mulPose(Axis.XP.rotation(xRot));
        poseStack.mulPose(Axis.YP.rotation(yRot));
        poseStack.mulPose(Axis.ZP.rotation(zRot));
        return poseStack.last().pose();
    }

    public static Matrix4f createMatrix(PoseStack poseStack, double xRot, double yRot, double zRot) {
        return createMatrix(poseStack, (float) xRot, (float) yRot, (float) zRot);
    }

    public static void setColor(EColor color) {
        RenderSystem.setShaderColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static void setTexture(ResourceLocation texture) {
        RenderSystem.setShaderTexture(0, texture);
    }

    public static void draw(BufferBuilder builder) {
        BufferUploader.drawWithShader(builder.buildOrThrow());
    }

    public enum CelestialRenderType {
        PRIMARY,
    }

}
