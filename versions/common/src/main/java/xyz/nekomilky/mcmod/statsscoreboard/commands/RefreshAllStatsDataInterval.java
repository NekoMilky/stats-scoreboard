package xyz.nekomilky.mcmod.statsscoreboard.commands;

import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.Component;
import xyz.nekomilky.mcmod.statsscoreboard.utils.command.CommandContext;
import xyz.nekomilky.mcmod.statsscoreboard.configs.SSBConfigManager;

public class RefreshAllStatsDataInterval {
	public static int getRefreshAllStatsDataInterval(CommandContext context) {
		var source = context.getSource();
		int interval = SSBConfigManager.modConfig.getRefreshAllStatsDataInterval();
		source.sendSuccess(Component.translate("statsscoreboard.command.success.get_refresh_all_stats_data_interval", interval), false);
		return 1;
	}

	public static int setRefreshAllStatsDataInterval(CommandContext context) {
		var source = context.getSource();
		int interval = context.getIntegerArgument("interval");
		var result = SSBConfigManager.modConfig.setRefreshAllStatsDataInterval(interval);
		if (!result.succeed) {
			source.sendFailure(result.result);
			return 0;
		}
		source.sendSuccess(result.result, false);
		return 1;
	}
}
