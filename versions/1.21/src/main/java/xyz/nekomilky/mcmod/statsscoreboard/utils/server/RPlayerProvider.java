package xyz.nekomilky.mcmod.statsscoreboard.utils.server;

import net.minecraft.server.network.ServerPlayerEntity;
import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.MutableComponent;
import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.RMutableComponent;

import java.util.UUID;

public class RPlayerProvider implements PlayerProvider {
	private final ServerPlayerEntity core;

	public RPlayerProvider(ServerPlayerEntity core) {
		this.core = core;
	}

	@Override
	public ServerPlayerEntity getCore() {
		return this.core;
	}

	@Override
	public String getName() {
		return this.core.getName().getString();
	}

	@Override
	public UUID getUUID() {
		return this.core.getUuid();
	}

	@Override
	public void saveStats() {
		this.core.getStatHandler().save();
	}

	@Override
	public void sendMessage(MutableComponent component) {
		this.core.sendMessage(((RMutableComponent) component).getCore());
	}
}
