package xyz.nekomilky.mcmod.statsscoreboard.utils.server;

public interface ServerContextProvider {
	Server getServer();

	Object getServerCore();

	void setServer(Object server);
}
