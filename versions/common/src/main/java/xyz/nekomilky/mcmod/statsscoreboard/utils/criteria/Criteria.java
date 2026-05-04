package xyz.nekomilky.mcmod.statsscoreboard.utils.criteria;

import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.Component;
import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

public class Criteria {
	final private static String MC_NAMESPACE_PREFIX = "minecraft";
	final private static String ALL_SUFFIX = "all";
	final private static String BLOCK_MINED = "block.mined.";
	final private static String BLOCK_PLACED = "block.placed.";
	final private static String CUSTOM = "custom.";
	final private static String ENTITY_KILLED = "entity.killed.";
	final private static String ENTITY_KILLED_BY = "entity.killed_by.";
	final private static String ITEM_BROKEN = "item.broken.";
	final private static String ITEM_CRAFTED = "item.crafted.";
	final private static String ITEM_DROPPED = "item.dropped.";
	final private static String ITEM_PICKED_UP = "item.picked_up.";
	final private static String ITEM_USED = "item.used.";
	final private static ArrayList<CriteriaMapping> CRITERIA_MAPPINGS = new ArrayList<>(List.of(
		new CriteriaMapping(BLOCK_MINED, "mined", "mined"),
		new CriteriaMapping(BLOCK_PLACED, "used", "placed"),
		new CriteriaMapping(CUSTOM, "custom", "custom"),
		new CriteriaMapping(ITEM_BROKEN, "broken", "broken"),
		new CriteriaMapping(ITEM_CRAFTED, "crafted", "crafted"),
		new CriteriaMapping(ITEM_DROPPED, "dropped", "dropped"),
		new CriteriaMapping(ITEM_PICKED_UP, "picked_up", "picked_up"),
		new CriteriaMapping(ITEM_USED, "used", "used"),
		new CriteriaMapping(ENTITY_KILLED, "killed", "killed"),
		new CriteriaMapping(ENTITY_KILLED_BY, "killed_by", "killed_by")
	));
	final private static List<String> criteria = new ArrayList<>();
	private static CriteriaProvider criteriaProvider = null;

	public static void setCriteriaProvider(CriteriaProvider provider) {
		criteriaProvider = provider;
	}

	public static boolean isBlock(String id) {
		return criteriaProvider.isBlock(id);
	}

    public static void init() {
		if (criteriaProvider == null) {
			return;
		}
        criteria.clear();
        criteria.addAll(criteriaProvider.getBlocks());
		criteria.addAll(criteriaProvider.getEntities());
		criteria.addAll(criteriaProvider.getItems());
		criteria.addAll(criteriaProvider.getCustoms());
    }

    public static List<String> get() {
        return criteria;
    }

	public static boolean isLegal(String c) {
		return criteria.contains(c);
	}

	public static MutableComponent getDisplayTitle(String c) {
		boolean isAll = c.endsWith(ALL_SUFFIX);
		MutableComponent allComponent = Component.translate("statsscoreboard.display_title.all");
		for (var entry : CRITERIA_MAPPINGS) {
			if (c.startsWith(entry.criteriaPrefix)) {
				String type = entry.criteriaPrefix.replace(entry.key + ".", "");
				String path = c.replace(entry.criteriaPrefix, "");
				// item check
				if (entry.criteriaPrefix.startsWith("item")) {
					if (criteriaProvider.isBlock(path)) {
						type = "block.";
					}
				}
				// custom check
				if (entry.criteriaPrefix.startsWith("custom")) {
					type = "stat.";
				}
				return Component.translate(
					"statsscoreboard.display_title." + entry.key,
					isAll ? allComponent : Component.translate(type + path)
				);
			}
		}
		return Component.translate("statsscoreboard.display_title.error");
	}

	public static ArrayList<String> getFullStatPath(String c) {
		for (var entry : CRITERIA_MAPPINGS) {
			if (c.startsWith(entry.criteriaPrefix)) {
				// type
				String type = MC_NAMESPACE_PREFIX + ":" + entry.key;
				// path
				String prefixRemoved = c.replace(entry.criteriaPrefix, "");
				return prefixRemoved.equals(ALL_SUFFIX)
					? new ArrayList<>(List.of("stats", type, ALL_SUFFIX))
					: (
						prefixRemoved.split("\\.").length == 2
						? new ArrayList<>(List.of("stats", type, prefixRemoved.replace(".", ":")))
						: new ArrayList<>()
					);
			}
		}
		return new ArrayList<>();
	}

	public static String getCriteriaFromStatName(String name, boolean isAll) {
		String[] parts = name.split(":");
		if (parts.length != 2) {
			return null;
		}
		String type = parts[0];
		String path = parts[1];
		for (var entry : CRITERIA_MAPPINGS) {
			String statType = entry.mojangStatType;
			String prefix = entry.criteriaPrefix;
			// used handle
			if (type.startsWith(MC_NAMESPACE_PREFIX + ".used")) {
				prefix = criteriaProvider.isBlock(path) ? BLOCK_PLACED : ITEM_USED;
			}
			if (type.startsWith(MC_NAMESPACE_PREFIX + "." + statType)) {
				return isAll ? prefix + ALL_SUFFIX : prefix + path;
			}
		}
		return null;
	}

	private static class CriteriaMapping {
		final String criteriaPrefix;
		final String mojangStatType;
		final String key;

		CriteriaMapping(String p, String s, String k) {
			this.criteriaPrefix = p;
			this.mojangStatType = s;
			this.key = k;
		}
	}
}
