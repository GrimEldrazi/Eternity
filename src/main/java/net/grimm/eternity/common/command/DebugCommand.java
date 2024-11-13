package net.grimm.eternity.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.grimm.eternity.Eternity;
import net.grimm.eternity.common.capability.DebugCap;
import net.grimm.eternity.common.data.DebugMode;
import net.grimm.eternity.common.util.Capability;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Objects;

public class DebugCommand {

    public DebugCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(Eternity.MOD_ID).requires(source -> source.hasPermission(4)).then(Commands.literal("debug")
                .then(Commands.literal("hud").executes(this::hud))
                .then(Commands.literal("orbitMap").executes(this::map))
                .then(Commands.literal("spaceFlightMode").executes(this::flight))));
    }

    private int hud(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer player = ctx.getSource().getPlayer();
        DebugCap cap = Capability.getDebugCap(player);
        DebugMode data = cap.getData();
        data.setHud(!data.isHud());
        cap.sync();
        return 0;
    }

    private int map(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer player = ctx.getSource().getPlayer();
        DebugCap cap = Capability.getDebugCap(player);
        DebugMode data = cap.getData();
        data.setMap(!data.isMap());
        cap.sync();
        return 0;
    }

    private int flight(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer player = ctx.getSource().getPlayer();
        DebugCap cap = Capability.getDebugCap(player);
        DebugMode data = cap.getData();
        data.setSpaceship(!data.isSpaceship());
        cap.sync();
        if (player != null) {
            ctx.getSource().sendSuccess(() ->
                    Component.literal(Objects.requireNonNull(player.getDisplayName()).getString() + " set their debug space flight mode to " + data.isSpaceship())
                            .withStyle(ChatFormatting.GRAY)
                            .withStyle(ChatFormatting.ITALIC),
                    true);
        }
        return 0;
    }

}
