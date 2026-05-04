package xyz.nekomilky.mcmod.statsscoreboard.utils.server;

import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.MutableComponent;

import java.util.UUID;

public class Player {
	final private PlayerProvider playerProvider;
	private static PlayerContextProvider playerContextProvider;

	public Player(PlayerProvider provider) {
		this.playerProvider = provider;
	}

	public PlayerProvider getProvider() {
		return this.playerProvider;
	}

	public String getName() {
		return this.playerProvider.getName();
	}

	public UUID getUUID() {
		return this.playerProvider.getUUID();
	}

	public void saveStats() {
		this.playerProvider.saveStats();
	}

	public void sendMessage(MutableComponent component) {
		this.playerProvider.sendMessage(component);
	}

	public static void setPlayerContextProvider(PlayerContextProvider provider) {
		playerContextProvider = provider;
	}

	public static Player getPlayer(Object player) {
		return playerContextProvider.getPlayer(player);
	}
}
