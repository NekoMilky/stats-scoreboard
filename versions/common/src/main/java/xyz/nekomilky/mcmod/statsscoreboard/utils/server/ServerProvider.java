package xyz.nekomilky.mcmod.statsscoreboard.utils.server;

import java.nio.file.Path;
import java.util.List;

public interface ServerProvider {
	List<Player> getPlayers();

	Path getWorldPath();
}
