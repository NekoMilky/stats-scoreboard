package xyz.nekomilky.mcmod.statsscoreboard.utils.event;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Player;

import java.util.function.Consumer;

public class RPlayerEventsProvider implements PlayerEventsProvider {
	@Override
	public void onPlayerJoined(Consumer<Player> callback) {
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			callback.accept(Player.getPlayer(handler.getPlayer()));
		});
	}

	@Override
	public void onPlayerStatIncrease(TriConsumer<Player, String, Integer> callback) {
		PlayerStatIncrease.EVENT.register(callback::accept);
	}
}
