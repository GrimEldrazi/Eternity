package net.grimm.eternity.client.render.environ;

import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public interface IFogRenderer {

    Vec3 render(@NotNull Vec3 vec3, float v);

}
