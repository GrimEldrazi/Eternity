package net.grimm.eternity.common.events;

import net.grimm.eternity.Eternity;
import net.grimm.eternity.common.astrodynamics.celestials.Celestial;
import net.grimm.eternity.common.astrodynamics.celestials.Moon;
import net.grimm.eternity.common.astrodynamics.celestials.Planet;
import net.grimm.eternity.common.command.DebugCommand;
import net.grimm.eternity.common.command.EpochControl;
import net.grimm.eternity.common.network.packets.CelestialDataSync;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = Eternity.MOD_ID)
public class CommonHandler {

    @SubscribeEvent
    public static void onRegisterReloadListeners(AddReloadListenerEvent event) {
        event.addListener(Planet.MANAGER);
        event.addListener(Moon.MANAGER);
    }

    @SubscribeEvent
    public static void onDataPackSync(OnDatapackSyncEvent event) {
        ServerPlayer player = event.getPlayer();
        if (player != null) {
            PacketDistributor.sendToPlayer(player, new CelestialDataSync(Celestial.encodeCelestialMap(Celestial.CELESTIALS)));
        } else {
            PacketDistributor.sendToAllPlayers(new CelestialDataSync(Celestial.encodeCelestialMap(Celestial.CELESTIALS)));
        }
    }

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        new DebugCommand(event.getDispatcher());
        new EpochControl(event.getDispatcher());
    }
}
