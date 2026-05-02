package xyz.nekomilky.mcmod.statsscoreboard.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import xyz.nekomilky.mcmod.statsscoreboard.configs.SSBConfigManager;

public class DefaultSidebar {
	public static int getDefaultSidebar(CommandContext<CommandSourceStack> context) {
		var source = context.getSource();
		String criteria = SSBConfigManager.modConfig.getDefaultSidebarCriteria();
		source.sendSuccess(() -> Component.translatable("statsscoreboard.command.success.get_default_sidebar", criteria), false);
		return 1;
	}

	public static int setDefaultSidebar(CommandContext<CommandSourceStack> context) {
		var source = context.getSource();
		String criteria = StringArgumentType.getString(context, "criteria");
		var result = SSBConfigManager.modConfig.setDefaultSidebarCriteria(criteria);
		if (!result.succeed) {
			source.sendFailure(result.result);
			return 0;
		}
		source.sendSuccess(() -> result.result, true);
		return 1;
	}
}
