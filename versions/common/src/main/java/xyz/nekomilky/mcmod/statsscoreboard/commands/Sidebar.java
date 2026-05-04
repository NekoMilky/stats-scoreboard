package xyz.nekomilky.mcmod.statsscoreboard.commands;

import xyz.nekomilky.mcmod.statsscoreboard.configs.SSBConfigManager;
import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.Component;
import xyz.nekomilky.mcmod.statsscoreboard.utils.command.CommandContext;
import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Player;

import java.util.List;

public class Sidebar {
	public static int getSidebar(CommandContext context) {
		var source = context.getSource();
		var entity = source.getEntity();
		if (entity instanceof Player player) {
			List<String> list = SSBConfigManager.playersConfig.getSelectedCriteria(player);
			source.sendSuccess(Component.translate("statsscoreboard.command.success.get_sidebar", list.toString()), false);
			return 1;
		}
		source.sendFailure(Component.translate("statsscoreboard.command.fail.player_only"));
		return 0;
	}

	public static int addSidebar(CommandContext context) {
		var source = context.getSource();
		var entity = source.getEntity();
		if (entity instanceof Player player) {
			String criteria = context.getStringArgument("criteria");
			var result = SSBConfigManager.playersConfig.addSelectedCriteria(player, criteria);
			if (!result.succeed) {
				source.sendFailure(result.result);
				return 0;
			}
			source.sendSuccess(result.result, true);
			xyz.nekomilky.mcmod.statsscoreboard.utils.sidebar.Sidebar.addSidebarForPlayer(player, criteria);
			return 1;
		}
		else {
			source.sendFailure(Component.translate("statsscoreboard.command.fail.player_only"));
			return 0;
		}
	}

	public static int removeSidebar(CommandContext context) {
		var source = context.getSource();
		var entity = source.getEntity();
		if (entity instanceof Player player) {
			String criteria = context.getStringArgument("criteria");
			var result = SSBConfigManager.playersConfig.removeSelectedCriteria(player, criteria);
			if (!result.succeed) {
				source.sendFailure(result.result);
				return 0;
			}
			source.sendSuccess(result.result, true);
			xyz.nekomilky.mcmod.statsscoreboard.utils.sidebar.Sidebar.removeSidebarForPlayer(player, criteria);
			return 1;
		}
		else {
			source.sendFailure(Component.translate("statsscoreboard.command.fail.player_only"));
			return 0;
		}
	}
}
