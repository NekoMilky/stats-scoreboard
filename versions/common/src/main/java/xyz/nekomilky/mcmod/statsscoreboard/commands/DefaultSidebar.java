package xyz.nekomilky.mcmod.statsscoreboard.commands;

import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.Component;
import xyz.nekomilky.mcmod.statsscoreboard.utils.command.CommandContext;
import xyz.nekomilky.mcmod.statsscoreboard.configs.SSBConfigManager;

public class DefaultSidebar {
	public static int getDefaultSidebar(CommandContext context) {
		var source = context.getSource();
		String criteria = SSBConfigManager.modConfig.getDefaultSidebarCriteria();
		source.sendSuccess(Component.translate("statsscoreboard.command.success.get_default_sidebar", criteria), false);
		return 1;
	}

	public static int setDefaultSidebar(CommandContext context) {
		var source = context.getSource();
		String criteria = context.getStringArgument("criteria");
		var result = SSBConfigManager.modConfig.setDefaultSidebarCriteria(criteria);
		if (!result.succeed) {
			source.sendFailure(result.result);
			return 0;
		}
		source.sendSuccess(result.result, true);
		return 1;
	}
}
