package net.grimm.eternity.client;

import net.grimm.eternity.Eternity;
import net.grimm.eternity.client.gui.DebugHUD;
import net.grimm.eternity.client.render.environ.special_effects.DimensionEffects;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = Eternity.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EternityClient {

    @SubscribeEvent
    public static void onRegisterGUI(RegisterGuiLayersEvent event) {
        event.registerBelow(VanillaGuiLayers.CHAT, Eternity.prefix("debug_hud"), new DebugHUD().register());
    }

    @SubscribeEvent
    public static void onDimensionEffectsRegister(RegisterDimensionSpecialEffectsEvent event) {
        DimensionEffects.EFFECTS.forEach(effect -> event.register(effect.getDimension(), effect));
    }

}
