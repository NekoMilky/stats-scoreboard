package xyz.nekomilky.mcmod.statsscoreboard.utils.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Player;

public interface PlayerStatIncrease {
	Event<PlayerStatIncrease> EVENT = EventFactory.createArrayBacked(
		PlayerStatIncrease.class,
		listeners -> (player, statName, count) -> {
			for (PlayerStatIncrease listener : listeners) {
				listener.interact(player, statName, count);
			}
		}
	);

	void interact(Player player, String statName, int count);
}
