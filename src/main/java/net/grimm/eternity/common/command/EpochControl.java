package net.grimm.eternity.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.grimm.eternity.Eternity;
import net.grimm.eternity.common.data.GlobalData;
import net.grimm.eternity.common.world.saved_data.Epoch;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.level.LevelAccessor;

public class EpochControl {

    public EpochControl(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(Eternity.MOD_ID).requires(source -> source.hasPermission(4)).then(Commands.literal("epoch")
                .then(Commands.literal("setEpoch").then(Commands.argument("epoch", LongArgumentType.longArg(0)).executes(this::epochSet)))
                .then(Commands.literal("setDelta").then(Commands.argument("delta", IntegerArgumentType.integer(0, 500)).executes(this::deltaSet)))));
    }

    private int epochSet(CommandContext<CommandSourceStack> ctx) {
        LevelAccessor level = ctx.getSource().getLevel();
        Epoch data = (Epoch) GlobalData.get(level, Epoch::new);
        data.setEpoch(LongArgumentType.getLong(ctx, "epoch"));
        return 0;
    }

    private int deltaSet(CommandContext<CommandSourceStack> ctx) {
        LevelAccessor level = ctx.getSource().getLevel();
        Epoch data = (Epoch) GlobalData.get(level, Epoch::new);
        data.setDelta(IntegerArgumentType.getInteger(ctx, "delta"));
        return 0;
    }

}
