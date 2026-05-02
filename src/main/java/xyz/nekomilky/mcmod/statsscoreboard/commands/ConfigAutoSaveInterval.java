package xyz.nekomilky.mcmod.statsscoreboard.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import xyz.nekomilky.mcmod.statsscoreboard.configs.SSBConfigManager;

public class ConfigAutoSaveInterval {
	public static int getConfigAutoSaveInterval(CommandContext<CommandSourceStack> context) {
		var source = context.getSource();
		int interval = SSBConfigManager.modConfig.getConfigAutoSaveInterval();
		source.sendSuccess(() -> Component.translatable("statsscoreboard.command.success.get_config_auto_save_interval", interval), false);
		return 1;
	}

	public static int setConfigAutoSaveInterval(CommandContext<CommandSourceStack> context) {
		var source = context.getSource();
		int interval = IntegerArgumentType.getInteger(context, "interval");
		var result = SSBConfigManager.modConfig.setConfigAutoSaveInterval(interval);
		if (!result.succeed) {
			source.sendFailure(result.result);
			return 0;
		}
		source.sendSuccess(() -> result.result, false);
		return 1;
	}
}
