package xyz.nekomilky.mcmod.statsscoreboard.utils.stat;

import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Server;

import java.nio.file.Path;

public class RStatsPathProvider implements StatsPathProvider {
	@Override
	public Path getStatsPath(Server server) {
		return server.getWorldPath().resolve("players").resolve("stats");
	}
}
