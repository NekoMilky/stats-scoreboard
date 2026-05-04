package xyz.nekomilky.mcmod.statsscoreboard.utils.sidebar;

import xyz.nekomilky.mcmod.statsscoreboard.StatsScoreboard;
import xyz.nekomilky.mcmod.statsscoreboard.configs.SSBConfigManager;
import xyz.nekomilky.mcmod.statsscoreboard.utils.criteria.Criteria;
import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Server;
import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Player;
import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.Component;
import xyz.nekomilky.mcmod.statsscoreboard.utils.stat.StatsReader;

import java.util.Map;
import java.util.UUID;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

public class Sidebar {
	final private static int MAX_PASSED_SECONDS = 365 * 24 * 60 * 60; // 1 year
	final private static Map<String, SidebarEntry> sidebars = new HashMap<>();
	final private static Set<String> refreshSet = new HashSet<>();
	private static int updateTick = 0;
	private static int passedSeconds = 0;
	private static SidebarProvider sidebarProvider = null;

	public static void setSidebarProvider(SidebarProvider provider) {
		sidebarProvider = provider;
	}

	public static void init() {
		sidebars.clear();
		refreshSet.clear();
		updateTick = 0;
		passedSeconds = 0;
	}

	public static void addSidebarForPlayer(Player player, String criteria) {
		player.saveStats();
		// check existing entry
		if (sidebars.containsKey(criteria)) {
			var sidebarEntry = sidebars.get(criteria);
			sidebarEntry.showToPlayers.add(player.getUUID());
		}
		// new entry
		else {
			if (sidebarProvider == null) {
				return;
			}
			SidebarEntry sidebarEntry = new SidebarEntry(sidebarProvider);
			var sidebar = sidebarEntry.sidebar;
			sidebarEntry.showToPlayers.add(player.getUUID());
			sidebars.put(criteria, sidebarEntry);
			// sidebar settings
			sidebar.setTitle(
				Criteria.getDisplayTitle(criteria).setStyle("YELLOW")
			);
			sidebar.show();
			// load immediately
			reloadSidebarData(Server.getServer(), criteria);
		}
		showSidebarToPlayer(player);
	}

	public static void removeSidebarForPlayer(Player player, String criteria) {
		SidebarEntry entry = sidebars.get(criteria);
		if (entry == null) {
			return;
		}
		entry.sidebar.removePlayer(player);
		entry.showToPlayers.remove(player.getUUID());
		if (entry.showToPlayers.isEmpty()) {
			sidebars.remove(criteria);
		}
		showSidebarToPlayer(player);
	}

	public static void showSidebarToPlayer(Player player) {
		List<String> list = SSBConfigManager.playersConfig.getSelectedCriteria(player);
		if (list.isEmpty()) {
			return;
		}
		int interval = SSBConfigManager.playersConfig.getSidebarRotationInterval(player);
		int period = list.size() * interval;
		String criteria = list.get((passedSeconds % period) / interval);
		for (String c : list) {
			if (!sidebars.containsKey(c)) {
				continue;
			}
			var sidebar = sidebars.get(c).sidebar;
			if (c.equals(criteria)) {
				sidebar.addPlayer(player);
			}
			else {
				sidebar.removePlayer(player);
			}
		}
	}

	public static void sidebarsTick(Server server) {
		updateTick++;
		if (updateTick >= 20) {
			updateTick = 0;
			passedSeconds++;
			// > 1 year reset
			if (passedSeconds > MAX_PASSED_SECONDS) {
				passedSeconds = 0;
			}
			// full reload stats data
			if (passedSeconds % (SSBConfigManager.modConfig.getRefreshAllStatsDataInterval() * 60) == 0) {
				reloadSidebarsData(server);
			}
			// debounced sidebar refresh display
			refreshSet.forEach(Sidebar::refreshSidebarDisplay);
			refreshSet.clear();
			// sidebar rotation detection
			for (Player player : server.getPlayers()) {
				showSidebarToPlayer(player);
			}
		}
	}

	public static void statUpdated(Player player, String criteria, int count) {
		SidebarEntry entry = sidebars.get(criteria);
		if (entry == null || count == 0) {
			return;
		}
		var records = entry.records;
		UUID uuid = player.getUUID();
		// update
		if (!records.containsKey(uuid)) {
			records.put(uuid, count);
		}
		else {
			records.put(uuid, records.get(uuid) + count);
		}
		refreshSet.add(criteria);
	}

	private static void reloadSidebarsData(Server server) {
		var players = server.getPlayers();
		players.forEach((player) -> {
			player.saveStats();
			player.sendMessage(
				Component.empty().append(StatsScoreboard.MOD_CHAT_PREFIX).append(
					Component.translate("statsscoreboard.chat.reload_sidebars_data").setStyle("YELLOW")
				)
			);
		});
		for (String key : sidebars.keySet()) {
			reloadSidebarData(server, key);
		}
		players.forEach((player) -> {
			player.sendMessage(
				Component.empty().append(StatsScoreboard.MOD_CHAT_PREFIX).append(
					Component.translate("statsscoreboard.chat.reload_sidebars_data_complete").setStyle("YELLOW")
				)
			);
		});
	}

	private static void reloadSidebarData(Server server, String criteria) {
		SidebarEntry entry = sidebars.get(criteria);
		if (entry == null) {
			return;
		}
		var records = entry.records;
		records.clear();
		var stats = StatsReader.readStats(server, Criteria.getFullStatPath(criteria));
		stats.forEach((stat) -> {
			int value = stat.value;
			if (value != 0) {
				records.put(stat.uuid, value);
			}
		});
		refreshSidebarDisplay(criteria);
	}

	private static void refreshSidebarDisplay(String criteria) {
		SidebarEntry entry = sidebars.get(criteria);
		if (entry == null) {
			return;
		}
		// sort
		Map<UUID, Integer> displayRecords = new HashMap<>();
		entry.records.entrySet().stream()
			.filter((record) -> {
				String playerName = SSBConfigManager.playersConfig.getPlayerName(record.getKey());
				if (playerName != null) {
					return true;
				}
				return SSBConfigManager.modConfig.getSidebarDisplayUUID();
			})
			.sorted(Map.Entry.<UUID, Integer>comparingByValue().reversed())
			.limit(10)
			.forEach((record) -> displayRecords.put(record.getKey(), record.getValue()));
		// display
		var sidebar = entry.sidebar;
		sidebar.clear();
		displayRecords.forEach((uuid, value) -> {
			String playerName = SSBConfigManager.playersConfig.getPlayerName(uuid);
			if (playerName == null) {
				playerName = "UUID(" + uuid.toString().substring(0, 8) + "...)";
			}
			sidebar.addLine(value, playerName);
		});
	}

	private static class SidebarEntry {
		public SidebarProvider sidebar;
		public ArrayList<UUID> showToPlayers = new ArrayList<>();
		public Map<UUID, Integer> records = new HashMap<>();

		public SidebarEntry(SidebarProvider provider) {
			this.sidebar = provider.create();
		}
	}
}
