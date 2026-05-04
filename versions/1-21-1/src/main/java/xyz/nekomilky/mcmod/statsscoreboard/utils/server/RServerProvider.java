package xyz.nekomilky.mcmod.statsscoreboard.utils.server;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RServerProvider implements ServerProvider {
	private final MinecraftServer core;

	public RServerProvider(MinecraftServer core) {
		this.core = core;
	}

	@Override
	public List<Player> getPlayers() {
		List<Player> result = new ArrayList<>();
		this.core.getPlayerManager().getPlayerList().forEach((player) -> {
			result.add(new Player(new RPlayerProvider(player)));
		});
		return result;
	}

	@Override
	public Path getWorldPath() {
		return this.core.getSavePath(WorldSavePath.ROOT);
	}
}
