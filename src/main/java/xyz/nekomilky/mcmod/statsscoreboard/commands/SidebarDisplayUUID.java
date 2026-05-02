package xyz.nekomilky.mcmod.statsscoreboard.commands;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import xyz.nekomilky.mcmod.statsscoreboard.configs.SSBConfigManager;

public class SidebarDisplayUUID {
	public static int getSidebarDisplayUUID(CommandContext<CommandSourceStack> context) {
		var source = context.getSource();
		boolean enabled = SSBConfigManager.modConfig.getSidebarDisplayUUID();
		source.sendSuccess(() -> Component.translatable("statsscoreboard.command.success.get_sidebar_display_uuid", Boolean.toString(enabled)), false);
		return 1;
	}

	public static int setSidebarDisplayUUID(CommandContext<CommandSourceStack> context) {
		var source = context.getSource();
		boolean enabled = BoolArgumentType.getBool(context, "enabled");
		var result = SSBConfigManager.modConfig.setSidebarDisplayUUID(enabled);
		if (!result.succeed) {
			source.sendFailure(result.result);
			return 0;
		}
		source.sendSuccess(() -> result.result, false);
		return 1;
	}
}
