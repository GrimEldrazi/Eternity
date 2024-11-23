package net.grimm.eternity.common.weather;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import org.jetbrains.annotations.NotNull;

public interface IWeatherTicker {

    void tick(@NotNull ClientLevel level, int ticks, @NotNull Camera camera);

}
