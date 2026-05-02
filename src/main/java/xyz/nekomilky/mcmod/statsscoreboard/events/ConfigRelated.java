package xyz.nekomilky.mcmod.statsscoreboard.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import xyz.nekomilky.mcmod.statsscoreboard.configs.SSBConfigManager;
import xyz.nekomilky.mcmod.statsscoreboard.utils.Context;
import xyz.nekomilky.mcmod.statsscoreboard.utils.Criteria;
import xyz.nekomilky.mcmod.statsscoreboard.utils.Sidebars;

public class ConfigRelated {
	// auto saver
	public static void init() {
		ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
			Context.setServer(server);
			Criteria.init();
		});
		ServerLifecycleEvents.SERVER_STOPPING.register((server) -> {
			SSBConfigManager.save();
		});
		ServerTickEvents.END_SERVER_TICK.register((server) -> {
			SSBConfigManager.autoSaveTick();
			Sidebars.sidebarsTick(server);
		});
	}
}
