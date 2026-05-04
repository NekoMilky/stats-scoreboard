package xyz.nekomilky.mcmod.statsscoreboard.commands;

import xyz.nekomilky.mcmod.statsscoreboard.configs.SSBConfigManager;
import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.Component;
import xyz.nekomilky.mcmod.statsscoreboard.utils.command.CommandContext;

public class ConfigAutoSaveInterval {
	public static int getConfigAutoSaveInterval(CommandContext context) {
		var source = context.getSource();
		int interval = SSBConfigManager.modConfig.getConfigAutoSaveInterval();
		source.sendSuccess(Component.translate("statsscoreboard.command.success.get_config_auto_save_interval", interval), false);
		return 1;
	}

	public static int setConfigAutoSaveInterval(CommandContext context) {
		var source = context.getSource();
		int interval = context.getIntegerArgument("interval");
		var result = SSBConfigManager.modConfig.setConfigAutoSaveInterval(interval);
		if (!result.succeed) {
			source.sendFailure(result.result);
			return 0;
		}
		source.sendSuccess(result.result, false);
		return 1;
	}
}
