package xyz.nekomilky.mcmod.statsscoreboard.events;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import xyz.nekomilky.mcmod.statsscoreboard.configs.SSBConfigManager;
import xyz.nekomilky.mcmod.statsscoreboard.utils.Criteria;
import xyz.nekomilky.mcmod.statsscoreboard.utils.Sidebars;

import java.util.List;

public class PlayerRelated {
	final private static List<StatType<?>> allowedStatTypes = List.of(new StatType<?>[] {
		Stats.BLOCK_MINED,
		Stats.CUSTOM,
		Stats.ENTITY_KILLED,
		Stats.ENTITY_KILLED_BY,
		Stats.ITEM_BROKEN,
		Stats.ITEM_CRAFTED,
		Stats.ITEM_DROPPED,
		Stats.ITEM_PICKED_UP,
		Stats.ITEM_USED
	});

	public static void init() {
		// join
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			var player = handler.getPlayer();
			List<String> list = SSBConfigManager.playersConfig.getSelectedCriteria(player);
			for (String criteria : list) {
				Sidebars.addSidebarForPlayer(player, criteria);
			}
			Sidebars.showSidebarToPlayer(player);
		});
		// stats change
		PlayerStatChangeCallback.EVENT.register((player, stat, count) -> {
			// check stat type
			if (!allowedStatTypes.contains(stat.getType())) {
				return;
			}
			// update
			String statName = stat.getName();
			String criteria = Criteria.getCriteriaFromStatName(statName, false);
			String criteriaAll = Criteria.getCriteriaFromStatName(statName, true);
			if (criteria != null) {
				Sidebars.statUpdated((ServerPlayer) player, criteria, count);
				if (!criteria.equals(criteriaAll)) {
					Sidebars.statUpdated((ServerPlayer) player, criteriaAll, count);
				}
			}
		});
	}
}
