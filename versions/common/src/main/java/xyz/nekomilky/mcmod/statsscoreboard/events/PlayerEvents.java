package xyz.nekomilky.mcmod.statsscoreboard.events;

import xyz.nekomilky.mcmod.statsscoreboard.configs.SSBConfigManager;
import xyz.nekomilky.mcmod.statsscoreboard.utils.criteria.Criteria;
import xyz.nekomilky.mcmod.statsscoreboard.utils.event.PlayerEventsProvider;
import xyz.nekomilky.mcmod.statsscoreboard.utils.sidebar.Sidebar;

import java.util.List;

public class PlayerEvents {
	private static PlayerEventsProvider playerEventsProvider = null;

	public static void setPlayerEventsProvider(PlayerEventsProvider provider) {
		playerEventsProvider = provider;
	}

	public static void init() {
		// join
		playerEventsProvider.onPlayerJoined((player) -> {
			List<String> list = SSBConfigManager.playersConfig.getSelectedCriteria(player);
			for (String criteria : list) {
				Sidebar.addSidebarForPlayer(player, criteria);
			}
			Sidebar.showSidebarToPlayer(player);
		});
		// stats change
		playerEventsProvider.onPlayerStatIncrease((player, statName, count) -> {
			String criteria = Criteria.getCriteriaFromStatName(statName, false);
			String criteriaAll = Criteria.getCriteriaFromStatName(statName, true);
			if (criteria != null) {
				Sidebar.statUpdated(player, criteria, count);
				if (!criteria.equals(criteriaAll)) {
					Sidebar.statUpdated(player, criteriaAll, count);
				}
			}
		});
	}
}
