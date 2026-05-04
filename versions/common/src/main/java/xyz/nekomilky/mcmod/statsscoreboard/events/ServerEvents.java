package xyz.nekomilky.mcmod.statsscoreboard.events;

import xyz.nekomilky.mcmod.statsscoreboard.configs.SSBConfigManager;
import xyz.nekomilky.mcmod.statsscoreboard.utils.criteria.Criteria;
import xyz.nekomilky.mcmod.statsscoreboard.utils.event.ServerEventsProvider;
import xyz.nekomilky.mcmod.statsscoreboard.utils.sidebar.Sidebar;

public class ServerEvents {
	private static ServerEventsProvider serverEventsProvider = null;

	public static void setServerEventsProvider(ServerEventsProvider provider) {
		serverEventsProvider = provider;
	}

	// auto saver
	public static void init() {
		serverEventsProvider.onServerStarted((server) -> {
			SSBConfigManager.init(server);
			Criteria.init();
			Sidebar.init();
		});

		serverEventsProvider.onServerStopping((server) -> {
			SSBConfigManager.save();
		});

		serverEventsProvider.onServerTick((server) -> {
			SSBConfigManager.autoSaveTick();
			Sidebar.sidebarsTick(server);
		});
	}
}
