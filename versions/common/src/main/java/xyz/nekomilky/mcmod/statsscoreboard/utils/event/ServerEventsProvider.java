package xyz.nekomilky.mcmod.statsscoreboard.utils.event;

import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Server;

import java.util.function.Consumer;

public interface ServerEventsProvider {
	void onServerStarted(Consumer<Server> callback);

	void onServerStopping(Consumer<Server> callback);

	void onServerTick(Consumer<Server> callback);
}
