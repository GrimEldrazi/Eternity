package net.grimm.eternity.test;

import net.grimm.eternity.Eternity;

import net.grimm.eternity.common.astrodynamics.celestials.Celestial;
import net.grimm.eternity.common.util.DayCycleUtil;
import net.grimm.eternity.common.world.saved_data.Epoch;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber(modid = Eternity.MOD_ID)
public class Test {

    @SubscribeEvent
    public static void test0(PlayerTickEvent.Post event) {
        Celestial iridium = Celestial.CELESTIALS.get("iridium");
        String msg = "" + Math.toDegrees(DayCycleUtil.sunAlt(iridium, Epoch.getInstance(event.getEntity().level()).getEpoch()));
        event.getEntity().displayClientMessage(Component.literal(msg), true);
    }

    @SubscribeEvent
    public static void test1(ServerTickEvent.Post event) {

    }

    @SubscribeEvent
    public static void test2(LevelTickEvent.Post event) {

    }

}