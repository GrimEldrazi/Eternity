package net.grimm.eternity.client.render.environ;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.grimm.eternity.Eternity;
import net.grimm.eternity.common.astrodynamics.celestials.Celestial;
import net.grimm.eternity.common.util.*;
import net.grimm.eternity.common.world.saved_data.Epoch;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class DynamicSky implements ISkyRender {

    private static final ResourceLocation SUN = Eternity.prefix("textures/sky/sun.png");
    private static final ResourceLocation TEST = Eternity.prefix("textures/sky/celestial_test.png");

    private PoseStack poseStack;
    private Celestial celestial;
    private long epoch;
    private Level level;
    private Player player;

    @Override
    public void render(@NotNull ClientLevel level, int ticks, float partialTick, @NotNull Matrix4f modelViewMatrix, @NotNull Camera camera, @NotNull Matrix4f projectionMatrix, boolean isFoggy, @NotNull Runnable setupFog) {
        this.poseStack = new PoseStack();
        this.celestial = LevelUtil.currentLevelCelestial(level);
        this.epoch = Epoch.getInstance(level).getEpoch();
        this.level = level;
        this.player = Minecraft.getInstance().player;
        poseStack.mulPose(modelViewMatrix);
        sun();
        //celestials();
        RenderUtil.setColor(new EColor(1, 1, 1, 1));
    }

    private void sun() {
        double yaw = celestial != null ? celestial.spinProgress(epoch) : 0;
        double pitch = celestial != null ? Mth.HALF_PI - celestial.getElements().latitude() : 0;
        poseStack.pushPose();
        RenderUtil.setColor(new EColor(1, 1, 1, 1));
        boolean isSpace = LevelUtil.isSpace(level) || this.celestial == null;
        Matrix4f matrix = RenderUtil.createSkyMatrix(poseStack, isSpace ?
                pitch - Mth.HALF_PI : pitch, 0, isSpace ? yaw + Math.PI : yaw);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderUtil.setTexture(SUN);
        BufferBuilder builder = RenderUtil.TESSELATOR.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        float size = Celestial.SUN_SIZE;
        builder.addVertex(matrix, -size, 100.0F, -size).setUv(0.0F, 0.0F);
        builder.addVertex(matrix, size, 100.0F, -size).setUv(1.0F, 0.0F);
        builder.addVertex(matrix, size, 100.0F, size).setUv(1.0F, 1.0F);
        builder.addVertex(matrix, -size, 100.0F, size).setUv(0.0F, 1.0F);
        RenderUtil.draw(builder);
        poseStack.popPose();
    }

    /*private void celestials() {
        poseStack.pushPose();
        for (Celestial celestial : Celestial.CELESTIALS.sortByDistance(epoch)) {
            boolean isSpace = LevelUtil.isSpace(level) || this.celestial == null;
            if (!isSpace && this.celestial.equals(celestial)) continue;
            for (RenderUtil.CelestialRenderType type : RenderUtil.CelestialRenderType.values()) {
                ResourceLocation texture = null;
                EColor color = new EColor(0, 0, 0, 0);
                switch (type) {
                    case PRIMARY -> {
                        color = new EColor(1, 1, 1, 1);
                        texture = TEST;
                    }
                }
                RenderUtil.setTexture(texture);
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderUtil.setColor(color);
                Parallax parallax;
                if (isSpace) {
                    parallax = new Parallax(new Vector3d(0), celestial, epoch); //TODO: set orbital entity position
                } else parallax = new CelestialParallax(this.celestial, celestial, epoch);
                SphericalCoordinates angPos = parallax.sphericalCoordinates();
                //Matrix4f matrix = RenderUtil.createSkyMatrix(poseStack, angPos.polarAng, 0, -angPos.azimuthAng); //TODO: test if render engine can handle objects at zenith. If not, control position in vertices rather than matrix
                Matrix4f matrix = RenderUtil.createSkyMatrix(poseStack, 0, 0, 0);
                BufferBuilder builder = RenderUtil.TESSELATOR.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                CubeMatrix cube = new CubeMatrix();
                cube.scale(Math.toDegrees(parallax.apparentRadius()));
                angPos = new Parallax(parallax).sphericalCoordinates();
                cube.rotate(angPos.azimuthAng, angPos.polarAng + Mth.HALF_PI);
                angPos.radius = 100;
                Vector3d pos = angPos.toCartesian();
                cube.translate(pos.x(), pos.y(), 100);
                float[][] nodes = cube.toMatrixF();
                for (int i = 0; i < 6; i++) {
                    CubeMatrix.PlanarNodes node = new CubeMatrix.PlanarNodes(nodes, i);
                    float f0 = i * (1.0F / 6);
                    float f1 = f0 + (1.0F / 6);
                    builder.addVertex(matrix, node.node0().x(), node.node0().z(), node.node0().y()).setUv(f0, 0);
                    builder.addVertex(matrix, node.node1().x(), node.node1().z(), node.node1().y()).setUv(f1, 0);
                    builder.addVertex(matrix, node.node2().x(), node.node2().z(), node.node2().y()).setUv(f1, 1);
                    builder.addVertex(matrix, node.node3().x(), node.node3().z(), node.node3().y()).setUv(f0, 1);
                }
                RenderUtil.draw(builder);
                poseStack.popPose();
            }
        }
    }*/

    /*private void test() {
        poseStack.pushPose();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        poseStack.mulPose(Axis.XP.rotation(0));
        poseStack.mulPose(Axis.YP.rotation(0));
        poseStack.mulPose(Axis.ZP.rotation(0));
        Matrix4f matrix = poseStack.last().pose();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, SUN);
        BufferBuilder builder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        float size = 10;
        builder.addVertex(matrix, -size, 100.0F, -size).setUv(0.0F, 0.0F);
        builder.addVertex(matrix, size, 100.0F, -size).setUv(1.0F, 0.0F);
        builder.addVertex(matrix, size, 100.0F, size).setUv(1.0F, 1.0F);
        builder.addVertex(matrix, -size, 100.0F, size).setUv(0.0F, 1.0F);
        BufferUploader.drawWithShader(builder.buildOrThrow());
        poseStack.popPose();
    }*/

}
