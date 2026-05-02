package xyz.nekomilky.mcmod.statsscoreboard.utils;

import net.minecraft.server.MinecraftServer;
import xyz.nekomilky.mcmod.statsscoreboard.configs.SSBConfigManager;

public class Context {
	private static MinecraftServer server;

	public static void setServer(MinecraftServer srv) {
		server = srv;
		SSBConfigManager.init(server);
	}

	public static MinecraftServer getServer() {
		return server;
	}
}
