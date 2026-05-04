package xyz.nekomilky.mcmod.statsscoreboard.utils.stat;

import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Server;

import java.nio.file.Path;

public interface StatsPathProvider {
	Path getStatsPath(Server server);
}
