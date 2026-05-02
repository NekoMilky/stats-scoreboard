package xyz.nekomilky.mcmod.statsscoreboard.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.stats.Stat;
import net.minecraft.world.entity.player.Player;

public interface PlayerStatChangeCallback {
	Event<PlayerStatChangeCallback> EVENT = EventFactory.createArrayBacked(
		PlayerStatChangeCallback.class,
		listeners -> (player, stat, count) -> {
			for (PlayerStatChangeCallback listener : listeners) {
				listener.interact(player, stat, count);
			}
		}
	);
	void interact(Player player, Stat<?> stat, int count);
}
