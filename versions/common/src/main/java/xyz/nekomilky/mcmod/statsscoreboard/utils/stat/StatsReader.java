package xyz.nekomilky.mcmod.statsscoreboard.utils.stat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import xyz.nekomilky.mcmod.statsscoreboard.StatsScoreboard;
import xyz.nekomilky.mcmod.statsscoreboard.configs.SSBConfigManager;
import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Server;
import xyz.nekomilky.mcmod.statsscoreboard.utils.criteria.Criteria;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class StatsReader {
	private static StatsPathProvider statsPathProvider = null;

	public static void setStatsPathProvider(StatsPathProvider provider) {
		statsPathProvider = provider;
	}

	public static ArrayList<StatEntry> readStats(Server server, ArrayList<String> path) {
		if (path.isEmpty() || statsPathProvider == null) {
			return new ArrayList<>();
		}
		Path statsPath = statsPathProvider.getStatsPath(server);
		if (!Files.exists(statsPath)) {
			StatsScoreboard.LOGGER.error("Can not resolve the directory " + statsPath + "! Stats read failed!");
			return new ArrayList<>();
		}
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(statsPath, "*.json")) {
			ArrayList<StatEntry> result = new ArrayList<>();
			for (Path jsonFilePath : stream) {
				StatEntry entry = readStat(jsonFilePath, path);
				if (entry != null) {
					result.add(entry);
				}
			}
			return result;
		}
		catch (IOException e) {
			StatsScoreboard.LOGGER.error("Stats read failed! " + e);
		}
		return new ArrayList<>();
	}

	private static StatEntry readStat(Path filePath, ArrayList<String> path) {
		// uuid
		String fileName = filePath.getFileName().toString();
		String uuidString = fileName.substring(0, fileName.lastIndexOf('.'));
		UUID uuid = UUID.fromString(uuidString);
		// playerName
		String playerName = SSBConfigManager.playersConfig.getPlayerName(uuid);
		if (playerName == null) {
			playerName = uuidString;
		}
		// value
		try {
			String NAMESPACE_PREFIX = "minecraft:";
			boolean isUsed = false;
			boolean isBlockOnly = false;
			JsonObject root = JsonParser.parseReader(Files.newBufferedReader(filePath)).getAsJsonObject();
			for (int i = 0; i < path.size() - 1; i++) {
				String layer = path.get(i);
				if (layer.equals(NAMESPACE_PREFIX + "placed") || layer.equals(NAMESPACE_PREFIX + "used")) {
					isUsed = true;
					isBlockOnly = layer.equals(NAMESPACE_PREFIX + "placed");
					layer = NAMESPACE_PREFIX + "used";
				}
				var next = root.get(layer);
				if (next == null) {
					return new StatEntry(uuid, playerName, 0);
				}
				root = next.getAsJsonObject();
				if (root == null || !root.isJsonObject()) {
					return new StatEntry(uuid, playerName, 0);
				}
			}
			int value = 0;
			if (path.getLast().equals("all")) {
				for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
					if (isUsed) {
						boolean isBlock = Criteria.isBlock(entry.getKey());
						if (isBlock ^ isBlockOnly) {
							continue;
						}
					}
					var node = entry.getValue();
					value += (node == null || !node.isJsonPrimitive()) ? 0 : node.getAsInt();
				}
			}
			else {
				var last = root.get(path.getLast());
				value = (last == null || !last.isJsonPrimitive()) ? 0 : last.getAsInt();
			}
			return new StatEntry(uuid, playerName, value);
		}
		catch (IOException e) {
			StatsScoreboard.LOGGER.error("Stats read failed! " + e);
		}
		return null;
	}

	public static class StatEntry {
		public UUID uuid;
		public String playerName;
		public int value;

		public StatEntry(UUID u, String n, int v) {
			this.uuid = u;
			this.playerName = n;
			this.value = v;
		}
	}
}
