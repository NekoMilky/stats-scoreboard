package xyz.nekomilky.mcmod.statsscoreboard.configs;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import xyz.nekomilky.mcmod.statsscoreboard.utils.Criteria;
import xyz.nekomilky.mcmod.statsscoreboard.utils.ExecutionResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayersConfig {
	private Map<UUID, PlayerEntry> players = new HashMap<>();

	public List<String> getSelectedCriteria(ServerPlayer player) {
		tryInitPlayer(player);
		return this.players.get(player.getUUID()).selectedCriteria;
	}

	public ExecutionResult<Component> addSelectedCriteria(ServerPlayer player, String value) {
		if (!Criteria.isLegal(value)) {
			return new ExecutionResult<>(false, Component.translatable("statsscoreboard.command.fail.criteria_illegal"));
		}
		List<String> list = getSelectedCriteria(player);
		if (list.contains(value)) {
			return new ExecutionResult<>(false, Component.translatable("statsscoreboard.command.fail.criteria_exists"));
		}
		list.add(value);
		setSelectedCriteria(player, list);
		return new ExecutionResult<>(true, Component.translatable("statsscoreboard.command.success.add_sidebar", value));
	}

	public ExecutionResult<Component> removeSelectedCriteria(ServerPlayer player, String value) {
		if (!Criteria.isLegal(value)) {
			return new ExecutionResult<>(false, Component.translatable("statsscoreboard.command.fail.criteria_illegal"));
		}
		List<String> list = getSelectedCriteria(player);
		if (!list.contains(value)) {
			return new ExecutionResult<>(false, Component.translatable("statsscoreboard.command.fail.criteria_does_not_exist"));
		}
		list.remove(value);
		setSelectedCriteria(player, list);
		return new ExecutionResult<>(true, Component.translatable("statsscoreboard.command.success.remove_sidebar", value));
	}

	public int getSidebarRotationInterval(ServerPlayer player) {
		tryInitPlayer(player);
		return this.players.get(player.getUUID()).sidebarRotationInterval;
	}

	public ExecutionResult<Component> setSidebarRotationInterval(ServerPlayer player, int value) {
		tryInitPlayer(player);
		if (value < 1) {
			return new ExecutionResult<>(false, Component.translatable("statsscoreboard.command.fail.value_illegal", "interval", value));
		}
		this.players.get(player.getUUID()).sidebarRotationInterval = value;
		return new ExecutionResult<>(true, Component.translatable("statsscoreboard.command.success.set_sidebar_rotation_interval", value));
	}

	public String getPlayerName(UUID uuid) {
		return hasPlayer(uuid) ? this.players.get(uuid).playerName : null;
	}

	private boolean hasPlayer(ServerPlayer player) {
		return hasPlayer(player.getUUID());
	}

	private boolean hasPlayer(UUID uuid) {
		return this.players.containsKey(uuid);
	}

	private void tryInitPlayer(ServerPlayer player) {
		if (!hasPlayer(player)) {
			this.players.put(player.getUUID(), new PlayerEntry(player.getName().getString()));
		}
	}

	private void setSelectedCriteria(ServerPlayer player, List<String> value) {
		tryInitPlayer(player);
		this.players.get(player.getUUID()).selectedCriteria = value;
	}

	private static class PlayerEntry {
		final private static String defaultCriteria = SSBConfigManager.modConfig.getDefaultSidebarCriteria();

		public List<String> selectedCriteria = new ArrayList<>(List.of(defaultCriteria));
		public String playerName;
		public int sidebarRotationInterval = 10;

		public PlayerEntry(String name) {
			this.playerName = name;
		}
	}
}
