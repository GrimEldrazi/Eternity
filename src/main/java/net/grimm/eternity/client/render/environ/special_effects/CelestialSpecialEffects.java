package net.grimm.eternity.client.render.environ.special_effects;

import com.mojang.blaze3d.vertex.PoseStack;
import net.grimm.eternity.client.render.environ.*;
import net.grimm.eternity.common.weather.IWeatherTicker;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class CelestialSpecialEffects extends DimensionSpecialEffects {

    private final ResourceLocation dimension;

    public CelestialSpecialEffects(ResourceLocation dimension) {
        super(Float.NaN, true, SkyType.NONE, false, false);
        this.dimension = dimension;
    }

    public ResourceLocation getDimension() {
        return dimension;
    }

    @Override
    public final @NotNull Vec3 getBrightnessDependentFogColor(@NotNull Vec3 vec3, float v) {
        if (fogRenderer() != null) {
            return fogRenderer().render(vec3, v);
        }
        return vec3;
    }

    @Override
    public boolean isFoggyAt(int i, int i1) {
        return false;
    }

    @Override
    public final boolean renderClouds(@NotNull ClientLevel level, int ticks, float partialTick, @NotNull PoseStack poseStack, double camX, double camY, double camZ, @NotNull Matrix4f modelViewMatrix, @NotNull Matrix4f projectionMatrix) {
        if (cloudRender() != null) {
            cloudRender().render(level, ticks, partialTick, poseStack, camX, camY, camZ, modelViewMatrix, projectionMatrix);
        }
        return true;
    }

    @Override
    public final boolean renderSky(@NotNull ClientLevel level, int ticks, float partialTick, @NotNull Matrix4f modelViewMatrix, @NotNull Camera camera, @NotNull Matrix4f projectionMatrix, boolean isFoggy, @NotNull Runnable setupFog) {
        if (skyRender() != null) {
            skyRender().render(level, ticks, partialTick, modelViewMatrix, camera, projectionMatrix, isFoggy, setupFog);
            if (postSkyRender() != null) {
                postSkyRender().render(level, ticks, partialTick, modelViewMatrix, camera, projectionMatrix, isFoggy, setupFog);
            }
        }
        return true;
    }

    @Override
    public final boolean renderSnowAndRain(@NotNull ClientLevel level, int ticks, float partialTick, @NotNull LightTexture lightTexture, double camX, double camY, double camZ) {
        if (precipitationRender() != null) {
            precipitationRender().render(level, ticks, partialTick, lightTexture, camX, camY, camZ);
        }
        return true;
    }

    @Override
    public final boolean tickRain(@NotNull ClientLevel level, int ticks, @NotNull Camera camera) {
        if (weatherTicker() != null) {
            weatherTicker().tick(level, ticks, camera);
        }
        return true;
    }

    @Override
    public final void adjustLightmapColors(@NotNull ClientLevel level, float partialTicks, float skyDarken, float blockLightRedFlicker, float skyLight, int pixelX, int pixelY, @NotNull Vector3f colors) {
        if (colorAdjuster() != null) {
            colorAdjuster().adjust(level, partialTicks, skyDarken, blockLightRedFlicker, skyLight, pixelX, pixelY, colors);
        }
    }

    public IFogRenderer fogRenderer() {
        return null;
    }

    public ICloudRender cloudRender() {
        return null;
    }

    public ISkyRender skyRender() {
        return new DynamicSky();
    }

    public ISkyRender postSkyRender() {
        return null;
    }

    public IPrecipitationRender precipitationRender() {
        return null;
    }

    public IWeatherTicker weatherTicker() {
        return null;
    }

    public ILightMapColorAdjuster colorAdjuster() {
        return null;
    }

}
