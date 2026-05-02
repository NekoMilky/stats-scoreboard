package xyz.nekomilky.mcmod.statsscoreboard.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import xyz.nekomilky.mcmod.statsscoreboard.configs.SSBConfigManager;

public class SidebarRotationInterval {
	public static int getSidebarRotationInterval(CommandContext<CommandSourceStack> context) {
		var source = context.getSource();
		var player = source.getEntity();
		if (!(player instanceof ServerPlayer)) {
			source.sendFailure(Component.translatable("statsscoreboard.command.fail.player_only"));
			return 0;
		}
		int interval = SSBConfigManager.playersConfig.getSidebarRotationInterval((ServerPlayer) player);
		source.sendSuccess(() -> Component.translatable("statsscoreboard.command.success.get_sidebar_rotation_interval", interval), false);
		return 1;
	}

	public static int setSidebarRotationInterval(CommandContext<CommandSourceStack> context) {
		var source = context.getSource();
		var player = source.getEntity();
		if (!(player instanceof ServerPlayer)) {
			source.sendFailure(Component.translatable("statsscoreboard.command.fail.player_only"));
			return 0;
		}
		int interval = IntegerArgumentType.getInteger(context, "interval");
		var result = SSBConfigManager.playersConfig.setSidebarRotationInterval((ServerPlayer) player, interval);
		if (!result.succeed) {
			source.sendFailure(result.result);
			return 0;
		}
		source.sendSuccess(() -> result.result, false);
		return 1;
	}
}
