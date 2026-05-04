package xyz.nekomilky.mcmod.statsscoreboard.commands;

import xyz.nekomilky.mcmod.statsscoreboard.StatsScoreboard;
import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.Component;
import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.MutableComponent;
import xyz.nekomilky.mcmod.statsscoreboard.utils.command.CommandContext;

import java.util.ArrayList;
import java.util.List;

public class About {
	final private static String commandPrefix = "/ssb ";
	final private static String connectSymbol = " - ";
	final private static List<MutableComponent> about = new ArrayList<>();

	public static int showAbout(CommandContext context) {
		var source = context.getSource();
		for (MutableComponent component : about) {
			source.sendSuccess(component, false);
		}
		return 1;
	}

	public static void init() {
		about.add(
			Component.literal(StatsScoreboard.MOD_NAME + " by " + StatsScoreboard.DEVELOPER).setStyle("LIGHT_PURPLE").append(
				Component.translate("statsscoreboard.about.overall").setStyle("GREEN")
			)
		);
		String[] commands = {"about", "configAutoSaveInterval", "defaultSidebar", "refreshAllStatsDataInterval", "sidebar", "sidebarDisplayUUID", "sidebarRotationInterval"};
		String[] translateKeys = {"about", "config_auto_save_interval", "default_sidebar", "refresh_all_stats_data_interval", "sidebar", "sidebar_display_uuid", "sidebar_rotation_interval"};
		for (int i = 0; i < commands.length; i++) {
			String command = commands[i];
			String translateKey = translateKeys[i];
			about.add(
				Component.literal(commandPrefix + command).setStyle("YELLOW").append(
					Component.literal(connectSymbol).setStyle("GRAY").append(
						Component.translate("statsscoreboard.about.command." + translateKey).setStyle("WHITE")
					)
				)
			);
		}
	}
}
