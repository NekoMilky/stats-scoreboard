package xyz.nekomilky.mcmod.statsscoreboard.commands;

import xyz.nekomilky.mcmod.statsscoreboard.configs.SSBConfigManager;
import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.Component;
import xyz.nekomilky.mcmod.statsscoreboard.utils.command.CommandContext;
import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Player;

public class SidebarRotationInterval {
	public static int getSidebarRotationInterval(CommandContext context) {
		var source = context.getSource();
		var entity = source.getEntity();
		if (entity instanceof Player player) {
			int interval = SSBConfigManager.playersConfig.getSidebarRotationInterval(player);
			source.sendSuccess(Component.translate("statsscoreboard.command.success.get_sidebar_rotation_interval", interval), false);
			return 1;
		}
		else {
			source.sendFailure(Component.translate("statsscoreboard.command.fail.player_only"));
			return 0;
		}
	}

	public static int setSidebarRotationInterval(CommandContext context) {
		var source = context.getSource();
		var entity = source.getEntity();
		if (entity instanceof Player player) {
			int interval = context.getIntegerArgument("interval");
			var result = SSBConfigManager.playersConfig.setSidebarRotationInterval(player, interval);
			if (!result.succeed) {
				source.sendFailure(result.result);
				return 0;
			}
			source.sendSuccess(result.result, false);
			return 1;
		}
		else {
			source.sendFailure(Component.translate("statsscoreboard.command.fail.player_only"));
			return 0;
		}
	}
}
