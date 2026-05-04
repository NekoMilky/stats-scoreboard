package xyz.nekomilky.mcmod.statsscoreboard.utils.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Player;

import java.util.function.Consumer;

public class RPlayerEventsProvider implements PlayerEventsProvider {
	@Override
	public void onPlayerJoined(Consumer<Player> callback) {
		ServerPlayerEvents.JOIN.register((player) -> {
			callback.accept(Player.getPlayer(player));
		});
	}

	@Override
	public void onPlayerStatIncrease(TriConsumer<Player, String, Integer> callback) {
		PlayerStatIncrease.EVENT.register(callback::accept);
	}
}
