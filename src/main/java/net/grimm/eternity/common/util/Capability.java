package net.grimm.eternity.common.util;

import net.grimm.eternity.common.capability.DebugCap;
import net.minecraft.world.entity.player.Player;

public final class Capability {

    public static DebugCap getDebugCap(Player player) {
        return new DebugCap(player);
    }

}
