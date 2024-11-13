package net.grimm.eternity.test;

import net.grimm.eternity.Eternity;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber(modid = Eternity.MOD_ID)
public class Test {

    @SubscribeEvent
    public static void test0(PlayerTickEvent.Post event) {

    }

    @SubscribeEvent
    public static void test1(ServerTickEvent.Post event) {
        Eternity.LOGGER.error("test var: {}", TestVariable.get(event.getServer().overworld()).getValue());
    }

    @SubscribeEvent
    public static void test2(LevelTickEvent.Post event) {
        if (event.getLevel().isClientSide) {
            return;
        }
        TestVariable.get(event.getLevel()).tick();
        TestVariable.get(event.getLevel()).sync(event.getLevel());
    }

}