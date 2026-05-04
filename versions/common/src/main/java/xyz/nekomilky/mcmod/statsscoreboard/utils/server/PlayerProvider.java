package xyz.nekomilky.mcmod.statsscoreboard.utils.server;

import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.MutableComponent;

import java.util.UUID;

public interface PlayerProvider {
	Object getCore();

	String getName();

	UUID getUUID();

	void saveStats();

	void sendMessage(MutableComponent component);
}
