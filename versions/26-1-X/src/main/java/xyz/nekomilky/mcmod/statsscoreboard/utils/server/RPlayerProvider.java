package xyz.nekomilky.mcmod.statsscoreboard.utils.server;

import net.minecraft.server.level.ServerPlayer;
import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.MutableComponent;
import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.RMutableComponent;

import java.util.UUID;

public class RPlayerProvider implements PlayerProvider {
	private final ServerPlayer core;

	public RPlayerProvider(ServerPlayer core) {
		this.core = core;
	}

	@Override
	public ServerPlayer getCore() {
		return this.core;
	}

	@Override
	public String getName() {
		return this.core.getName().getString();
	}

	@Override
	public UUID getUUID() {
		return this.core.getUUID();
	}

	@Override
	public void saveStats() {
		this.core.getStats().save();
	}

	@Override
	public void sendMessage(MutableComponent component) {
		this.core.sendSystemMessage(((RMutableComponent) component).getCore());
	}
}
