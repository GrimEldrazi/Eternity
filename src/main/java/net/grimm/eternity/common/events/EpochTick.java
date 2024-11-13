package net.grimm.eternity.common.events;

import net.grimm.eternity.Eternity;
import net.grimm.eternity.common.data.GlobalData;
import net.grimm.eternity.common.world.saved_data.Epoch;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = Eternity.MOD_ID)
public class EpochTick {

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        Epoch data = (Epoch) GlobalData.get(event.getLevel(), Epoch::new);
        data.tick();
        data.sync(event.getLevel());
    }
}
