package xyz.nekomilky.mcmod.statsscoreboard.utils.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Server;

import java.util.function.Consumer;

public class RServerEventsProvider implements ServerEventsProvider {
	@Override
	public void onServerStarted(Consumer<Server> callback) {
		ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
			Server.setServer(server);
			callback.accept(Server.getServer());
		});
	}

	@Override
	public void onServerStopping(Consumer<Server> callback) {
		ServerLifecycleEvents.SERVER_STOPPING.register((server) -> {
			callback.accept(Server.getServer());
		});
	}

	@Override
	public void onServerTick(Consumer<Server> callback) {
		ServerTickEvents.END_SERVER_TICK.register((server) -> {
			callback.accept(Server.getServer());
		});
	}
}
