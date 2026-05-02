package xyz.nekomilky.mcmod.statsscoreboard.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import xyz.nekomilky.mcmod.statsscoreboard.configs.SSBConfigManager;
import xyz.nekomilky.mcmod.statsscoreboard.utils.Sidebars;

import java.util.List;

public class Sidebar {
	public static int getSidebar(CommandContext<CommandSourceStack> context) {
		var source = context.getSource();
		var player = source.getEntity();
		if (!(player instanceof ServerPlayer)) {
			source.sendFailure(Component.translatable("statsscoreboard.command.fail.player_only"));
			return 0;
		}
		List<String> list = SSBConfigManager.playersConfig.getSelectedCriteria((ServerPlayer) player);
		source.sendSuccess(() -> Component.translatable("statsscoreboard.command.success.get_sidebar", list.toString()), false);
		return 1;
	}

	public static int addSidebar(CommandContext<CommandSourceStack> context) {
		var source = context.getSource();
		var player = source.getEntity();
		if (!(player instanceof ServerPlayer)) {
			source.sendFailure(Component.translatable("statsscoreboard.command.fail.player_only"));
			return 0;
		}
		String criteria = StringArgumentType.getString(context, "criteria");
		var result = SSBConfigManager.playersConfig.addSelectedCriteria((ServerPlayer) player, criteria);
		if (!result.succeed) {
			source.sendFailure(result.result);
			return 0;
		}
		source.sendSuccess(() -> result.result, true);
		Sidebars.addSidebarForPlayer((ServerPlayer) player, criteria);
		return 1;
	}

	public static int removeSidebar(CommandContext<CommandSourceStack> context) {
		var source = context.getSource();
		var player = source.getEntity();
		if (!(player instanceof ServerPlayer)) {
			source.sendFailure(Component.translatable("statsscoreboard.command.fail.player_only"));
			return 0;
		}
		String criteria = StringArgumentType.getString(context, "criteria");
		var result = SSBConfigManager.playersConfig.removeSelectedCriteria((ServerPlayer) player, criteria);
		if (!result.succeed) {
			source.sendFailure(result.result);
			return 0;
		}
		source.sendSuccess(() -> result.result, true);
		Sidebars.removeSidebarForPlayer((ServerPlayer) player, criteria);
		return 1;
	}
}
