package xyz.nekomilky.mcmod.statsscoreboard.utils.server;

import java.nio.file.Path;
import java.util.List;

public class Server {
	final private ServerProvider serverProvider;
	private static ServerContextProvider serverContextProvider;

	public Server(ServerProvider provider) {
		this.serverProvider = provider;
	}

	public List<Player> getPlayers() {
		return this.serverProvider.getPlayers();
	}

	public Path getWorldPath() {
		return this.serverProvider.getWorldPath();
	}

	public static void setServerContextProvider(ServerContextProvider provider) {
		serverContextProvider = provider;
	}

	public static Server getServer() {
		return serverContextProvider.getServer();
	}

	public static Object getServerCore() {
		return serverContextProvider.getServerCore();
	}

	public static void setServer(Object server) {
		serverContextProvider.setServer(server);
	}
}
