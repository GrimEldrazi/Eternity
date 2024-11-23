package net.grimm.eternity.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.grimm.eternity.Eternity;
import net.grimm.eternity.common.astrodynamics.celestials.Celestial;
import net.grimm.eternity.common.astrodynamics.orbits.KeplerianOrbitTEST;
import net.grimm.eternity.common.astrodynamics.orbits.OrbitTEST;
import net.grimm.eternity.common.astrodynamics.orbits.OrbitType;
import net.grimm.eternity.common.data.GlobalData;
import net.grimm.eternity.common.util.Capability;
import net.grimm.eternity.common.util.EColor;
import net.grimm.eternity.common.util.EMath;
import net.grimm.eternity.common.world.saved_data.Epoch;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import org.joml.Vector2d;

public class DebugHUD implements IOverlay {

    private static final ResourceLocation SUN = Eternity.prefix("textures/sky/sun.png");
    private static final ResourceLocation ICON = Eternity.prefix("textures/gui/celestial_icon.png");
    private static final ResourceLocation MARKER = Eternity.prefix("textures/gui/orbit_marker.png");

    @Override
    public LayeredDraw.Layer register() {
        return this::render;
    }

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        if (!minecraft.options.hideGui) {
            Font font = minecraft.font;
            LevelAccessor level = minecraft.level;
            Player player = minecraft.player;
            if (player != null && level != null && Capability.getDebugCap(player).getData().isHud()) {
                RenderSystem.setShaderColor(0.3F, 0.3F, 0.3F, 0.3F);
                RenderSystem.enableBlend();
                guiGraphics.blit(SUN, 0, 0, 0, 0, 500, 300, 100, 100);
                RenderSystem.setShaderColor(1, 1, 1, 1);
                Epoch data = ((Epoch) GlobalData.get(level, Epoch::new));
                long epoch = data.getEpoch();
                int delta = data.getDelta();
                int x = 0, y = 0;
                guiGraphics.drawCenteredString(font, Component.literal("debug information hud".toUpperCase()).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD), 250, y, -1);
                guiGraphics.drawString(font, Component.literal("Orbit Data").withStyle(ChatFormatting.UNDERLINE), x, y, -1);
                y = 150;
                guiGraphics.drawString(font, Component.literal("Global Variables").withStyle(ChatFormatting.UNDERLINE), 0, y, -1);
                guiGraphics.drawString(font, Component.literal("Epoch: " + epoch + " (+" + delta + ")"), x, y + 15, -1);
                final int[] yOffset = {0};
                int xPos = x + 5;
                Celestial.CELESTIALS.sortAsList().forEach(celestial -> {
                    OrbitTEST orbitTEST = celestial.getOrbit();
                    orbitTEST.propagateIfKeplerian(epoch);
                    yOffset[0] += 10;
                    int yPos = 5 + yOffset[0];
                    EColor color = celestial.getColorID();
                    guiGraphics.drawString(font, Component.literal(celestial.getName().toUpperCase() + ":").withStyle(ChatFormatting.BOLD), xPos, 5 + yOffset[0], color.toInt());
                    RenderSystem.setShaderColor(1, 1, 1, 1);
                    guiGraphics.drawString(font, Component.literal("a: " + String.format("%e", orbitTEST.a())).withStyle(ChatFormatting.ITALIC), x + 75, yPos, -1);
                    guiGraphics.drawString(font, Component.literal("e: " + orbitTEST.e()).withStyle(ChatFormatting.ITALIC), xPos + 165, yPos, -1);
                    guiGraphics.drawString(font, Component.literal("i: " + Math.toDegrees(orbitTEST.i())).withStyle(ChatFormatting.ITALIC), x + 220, yPos, -1);
                    guiGraphics.drawString(font, Component.literal("raan: " + Math.toDegrees(orbitTEST.raan())).withStyle(ChatFormatting.ITALIC), xPos + 270, yPos, -1);
                    guiGraphics.drawString(font, Component.literal("w: " + Math.toDegrees(orbitTEST.omega())).withStyle(ChatFormatting.ITALIC), xPos + 340, yPos, -1);
                    guiGraphics.drawString(font, Component.literal("v: " + EMath.round(Math.toDegrees(orbitTEST.v()), 2)).withStyle(ChatFormatting.ITALIC), xPos + 400, yPos, -1);
                });
            }
            if (player != null && level != null && Capability.getDebugCap(player).getData().isMap()) {
                Epoch data = (Epoch) GlobalData.get(level, Epoch::new);
                long epoch = data.getEpoch();
                RenderSystem.setShaderColor(0.3F, 0.3F, 0.3F, 0.6F);
                RenderSystem.enableBlend();
                guiGraphics.blit(SUN, 0, 0, 0, 0, 500, 300, 100, 100);
                RenderSystem.setShaderColor(1, 1, 1, 1);
                Celestial.CELESTIALS.sortAsList().forEach(celestial -> {
                    OrbitTEST orbitTEST = celestial.getOrbit();
                    int xAnchor = 240;
                    int yAnchor = 135;
                    int size = celestial.isPlanet() ? 10 : 5;
                    double scalar = 5E-7;
                    int quant = 1000;
                    if (orbitTEST.type() == OrbitType.KEPLERIAN) {
                        for (int i = 0; i < quant; i++) {
                            long futureEpoch = (long) (orbitTEST.orbitPeriod() * ((double) i / quant));
                            KeplerianOrbitTEST propagatedOrbit = (KeplerianOrbitTEST) orbitTEST;
                            propagatedOrbit.staticPropagation(futureEpoch);
                            Vector2d pos = new Vector2d(propagatedOrbit.position().x(), propagatedOrbit.position().y())
                                    .mul(scalar)
                                    .add(xAnchor, yAnchor);
                            RenderSystem.setShaderColor(1, 1, 1, 0.7F);
                            guiGraphics.blit(MARKER, Mth.floor(pos.x()), Mth.floor(pos.y()), 0, 0, 1, 1, 1, 1);
                            RenderSystem.setShaderColor(1, 1, 1, 1);
                        }
                    }
                    orbitTEST.propagateIfKeplerian(epoch);
                    Vector2d pos = new Vector2d(orbitTEST.position().x(), orbitTEST.position().y())
                            .mul(scalar)
                            .add(xAnchor, yAnchor)
                            .sub((double) size / 2, (double) size / 2);
                    EColor color = celestial.getColorID();
                    color = color.norm();
                    RenderSystem.setShaderColor(color.getRed(), color.getGreen(), color.getBlue(), 1);
                    guiGraphics.blit(ICON, Mth.floor(pos.x()), Mth.floor(pos.y()), 0, 0, size, size, size, size);
                });
            }
            RenderSystem.setShaderColor(1, 1, 1, 1);
            RenderSystem.disableBlend();
        }
    }
}
