package xyz.nekomilky.mcmod.statsscoreboard.commands;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import xyz.nekomilky.mcmod.statsscoreboard.StatsScoreboard;

import java.util.ArrayList;
import java.util.List;

public class About {
	final private static String commandPrefix = "/ssb ";
	final private static String connectSymbol = " - ";
	final private static List<Component> about = new ArrayList<>();

	public static int showAbout(CommandContext<CommandSourceStack> context) {
		var source = context.getSource();
		for (Component component : about) {
			source.sendSuccess(() -> component, false);
		}
		return 1;
	}

	public static void init() {
		about.add(
			Component.literal(StatsScoreboard.MOD_NAME + " by " + StatsScoreboard.DEVELOPER).setStyle(Style.EMPTY.withColor(ChatFormatting.LIGHT_PURPLE)).append(
				Component.translatable("statsscoreboard.about.overall").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))
			)
		);
		String[] commands = {"about", "configAutoSaveInterval", "defaultSidebar", "refreshAllStatsDataInterval", "sidebar", "sidebarDisplayUUID", "sidebarRotationInterval"};
		String[] translateKeys = {"about", "config_auto_save_interval", "default_sidebar", "refresh_all_stats_data_interval", "sidebar", "sidebar_display_uuid", "sidebar_rotation_interval"};
		for (int i = 0; i < commands.length; i++) {
			String command = commands[i];
			String translateKey = translateKeys[i];
			about.add(
				Component.literal(commandPrefix + command).setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW)).append(
					Component.literal(connectSymbol).setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)).append(
						Component.translatable("statsscoreboard.about.command." + translateKey).setStyle(Style.EMPTY.withColor(ChatFormatting.WHITE))
					)
				)
			);
		}
	}
}
