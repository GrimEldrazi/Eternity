package net.grimm.eternity.common.events;

import net.grimm.eternity.Eternity;
import net.grimm.eternity.common.util.Capability;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = Eternity.MOD_ID)
public class CapabilityHandler {

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            Capability.getDebugCap(player).sync();
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            Capability.getDebugCap(player).sync();
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            Capability.getDebugCap(player).sync();
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {

        }
    }
}
