package xyz.nekomilky.mcmod.statsscoreboard.utils;

import eu.pb4.sidebars.api.Sidebar;
import eu.pb4.sidebars.api.lines.SidebarLine;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import xyz.nekomilky.mcmod.statsscoreboard.StatsScoreboard;
import xyz.nekomilky.mcmod.statsscoreboard.configs.SSBConfigManager;

import java.util.Map;
import java.util.UUID;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

public class Sidebars {
	final private static int MAX_PASSED_SECONDS = 365 * 24 * 60 * 60; // 1 year
	final private static Map<String, SidebarEntry> sidebars = new HashMap<>();
	final private static Set<String> refreshSet = new HashSet<>();
	private static int updateTick = 0;
	private static int passedSeconds = 0;

	public static void addSidebarForPlayer(ServerPlayer player, String criteria) {
		player.getStats().save();
		// check existing entry
		if (sidebars.containsKey(criteria)) {
			var sidebarEntry = sidebars.get(criteria);
			sidebarEntry.showToPlayers.add(player.getUUID());
		}
		// new entry
		else {
			SidebarEntry sidebarEntry = new SidebarEntry();
			var sidebar = sidebarEntry.sidebar;
			sidebarEntry.showToPlayers.add(player.getUUID());
			sidebars.put(criteria, sidebarEntry);
			// sidebar settings
			sidebar.setTitle(
				Criteria.getDisplayTitle(criteria).setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW))
			);
			sidebar.show();
			// load immediately
			reloadSidebarData(Context.getServer(), criteria);
		}
		showSidebarToPlayer(player);
	}

	public static void removeSidebarForPlayer(ServerPlayer player, String criteria) {
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

	public static void showSidebarToPlayer(ServerPlayer player) {
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
			Sidebar sidebar = sidebars.get(c).sidebar;
			if (c.equals(criteria)) {
				sidebar.addPlayer(player);
			}
			else {
				sidebar.removePlayer(player);
			}
		}
	}

	public static void sidebarsTick(MinecraftServer server) {
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
			refreshSet.forEach(Sidebars::refreshSidebarDisplay);
			refreshSet.clear();
			// sidebar rotation detection
			for (ServerPlayer player : server.getPlayerList().getPlayers()) {
				showSidebarToPlayer(player);
			}
		}
	}

	public static void statUpdated(ServerPlayer player, String criteria, int count) {
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

	private static void reloadSidebarsData(MinecraftServer server) {
		var players = server.getPlayerList().getPlayers();
		players.forEach((player) -> {
			player.getStats().save();
			player.sendSystemMessage(
				Component.empty().append(StatsScoreboard.MOD_CHAT_PREFIX).append(
					Component.translatable("statsscoreboard.chat.reload_sidebars_data").setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW))
				)
			);
		});
		for (String key : sidebars.keySet()) {
			reloadSidebarData(server, key);
		}
		players.forEach((player) -> {
			player.sendSystemMessage(
				Component.empty().append(StatsScoreboard.MOD_CHAT_PREFIX).append(
					Component.translatable("statsscoreboard.chat.reload_sidebars_data_complete").setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW))
				)
			);
		});
	}

	private static void reloadSidebarData(MinecraftServer server, String criteria) {
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
			.sorted(Map.Entry.<UUID, Integer>comparingByValue().reversed())
			.limit(10)
			.forEach((record) -> displayRecords.put(record.getKey(), record.getValue()));
		// display
		Sidebar sidebar = entry.sidebar;
		sidebar.clearLines();
		displayRecords.forEach((uuid, value) -> {
			String playerName = SSBConfigManager.playersConfig.getPlayerName(uuid);
			if (playerName == null) {
				if (!SSBConfigManager.modConfig.getSidebarDisplayUUID()) {
					return;
				}
				playerName = "UUID(" + uuid.toString().substring(0, 8) + "...)";
			}
			sidebar.addLines(SidebarLine.create(
				value,
				Component.literal(playerName)
			));
		});
	}

	private static class SidebarEntry {
		public Sidebar sidebar = new Sidebar(Sidebar.Priority.HIGH);
		public ArrayList<UUID> showToPlayers = new ArrayList<>();
		public Map<UUID, Integer> records = new HashMap<>();
	}
}
