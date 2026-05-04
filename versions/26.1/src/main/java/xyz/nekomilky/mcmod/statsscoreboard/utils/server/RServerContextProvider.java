package xyz.nekomilky.mcmod.statsscoreboard.utils.server;

import net.minecraft.server.MinecraftServer;

public class RServerContextProvider implements ServerContextProvider {
	private MinecraftServer serverCore;
	private Server serverCache;

	@Override
	public Server getServer() {
		return this.serverCache;
	}

	@Override
	public MinecraftServer getServerCore() {
		return this.serverCore;
	}

	@Override
	public void setServer(Object s) {
		MinecraftServer server = (MinecraftServer) s;
		this.serverCore = server;
		this.serverCache = new Server(new RServerProvider(server));
	}
}
