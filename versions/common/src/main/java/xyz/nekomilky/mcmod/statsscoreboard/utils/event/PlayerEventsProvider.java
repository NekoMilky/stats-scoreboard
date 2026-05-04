package xyz.nekomilky.mcmod.statsscoreboard.utils.event;

import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Player;

import java.util.function.Consumer;

public interface PlayerEventsProvider {
	void onPlayerJoined(Consumer<Player> callback);

	void onPlayerStatIncrease(TriConsumer<Player, String, Integer> callback);
}
