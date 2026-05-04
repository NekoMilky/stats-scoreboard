package xyz.nekomilky.mcmod.statsscoreboard.commands;

import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.Component;
import xyz.nekomilky.mcmod.statsscoreboard.utils.command.CommandContext;
import xyz.nekomilky.mcmod.statsscoreboard.configs.SSBConfigManager;

public class SidebarDisplayUUID {
	public static int getSidebarDisplayUUID(CommandContext context) {
		var source = context.getSource();
		boolean enabled = SSBConfigManager.modConfig.getSidebarDisplayUUID();
		source.sendSuccess(Component.translate("statsscoreboard.command.success.get_sidebar_display_uuid", Boolean.toString(enabled)), false);
		return 1;
	}

	public static int setSidebarDisplayUUID(CommandContext context) {
		var source = context.getSource();
		boolean enabled = context.getBoolArgument("enabled");
		var result = SSBConfigManager.modConfig.setSidebarDisplayUUID(enabled);
		if (!result.succeed) {
			source.sendFailure(result.result);
			return 0;
		}
		source.sendSuccess(result.result, false);
		return 1;
	}
}
