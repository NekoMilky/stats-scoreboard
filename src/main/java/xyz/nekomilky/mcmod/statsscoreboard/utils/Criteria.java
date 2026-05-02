package xyz.nekomilky.mcmod.statsscoreboard.utils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;

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

    public static void init() {
        criteria.clear();
        // block
        criteria.addAll(getBlocks());
		// entity
		criteria.addAll(getEntities());
		// item
		criteria.addAll(getItems());
		// custom
		criteria.addAll(getCustoms());
    }

    public static List<String> get() {
        return criteria;
    }

	public static boolean isLegal(String c) {
		return criteria.contains(c);
	}

	public static MutableComponent getDisplayTitle(String c) {
		boolean isAll = c.endsWith(ALL_SUFFIX);
		Component allComponent = Component.translatable("statsscoreboard.display_title.all");
		for (var entry : CRITERIA_MAPPINGS) {
			if (c.startsWith(entry.criteriaPrefix)) {
				String type = entry.criteriaPrefix.replace(entry.key + ".", "");
				String path = c.replace(entry.criteriaPrefix, "");
				// item check
				if (entry.criteriaPrefix.startsWith("item")) {
					var identifier = Identifier.tryBySeparator(path, '.');
					if (identifier != null && BuiltInRegistries.BLOCK.get(identifier).isPresent()) {
						type = "block.";
					}
				}
				// custom check
				if (entry.criteriaPrefix.startsWith("custom")) {
					type = "stat.";
				}
				return Component.translatable(
					"statsscoreboard.display_title." + entry.key,
					isAll ? allComponent : Component.translatable(type + path)
				);
			}
		}
		return Component.translatable("statsscoreboard.display_title.error");
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
				var identifier = Identifier.tryBySeparator(path, '.');
				if (identifier == null) {
					continue;
				}
				boolean isBlock = BuiltInRegistries.BLOCK.get(identifier).isPresent();
				prefix = isBlock ? BLOCK_PLACED : ITEM_USED;
			}
			if (type.startsWith(MC_NAMESPACE_PREFIX + "." + statType)) {
				return isAll ? prefix + ALL_SUFFIX : prefix + path;
			}
		}
		return null;
	}

    private static List<String> getBlocks() {
        List<String> list = new ArrayList<>();
        for (var block : BuiltInRegistries.BLOCK) {
			var key = BuiltInRegistries.BLOCK.getKey(block);
			String id = key.getNamespace() + "." + key.getPath();
            list.add(BLOCK_MINED + id);
			list.add(BLOCK_PLACED + id);
        }
        list.add(BLOCK_MINED + ALL_SUFFIX);
		list.add(BLOCK_PLACED + ALL_SUFFIX);
        return list;
    }

	private static List<String> getEntities() {
		List<String> list = new ArrayList<>();
		for (var entity : BuiltInRegistries.ENTITY_TYPE) {
			var key = BuiltInRegistries.ENTITY_TYPE.getKey(entity);
			String id = key.getNamespace() + "." + key.getPath();
			list.add(ENTITY_KILLED + id);
			list.add(ENTITY_KILLED_BY + id);
		}
		list.add(ENTITY_KILLED + ALL_SUFFIX);
		list.add(ENTITY_KILLED_BY + ALL_SUFFIX);
		return list;
	}

	private static List<String> getItems() {
		List<String> list = new ArrayList<>();
		for (var item : BuiltInRegistries.ITEM) {
			var key = BuiltInRegistries.ITEM.getKey(item);
			String id = key.getNamespace() + "." + key.getPath();
			list.add(ITEM_CRAFTED + id);
			list.add(ITEM_DROPPED + id);
			list.add(ITEM_PICKED_UP + id);
			if (BuiltInRegistries.BLOCK.get(key).isEmpty()) {
				list.add(ITEM_BROKEN + id);
				list.add(ITEM_USED + id);
			}
		}
		list.add(ITEM_BROKEN + ALL_SUFFIX);
		list.add(ITEM_CRAFTED + ALL_SUFFIX);
		list.add(ITEM_DROPPED + ALL_SUFFIX);
		list.add(ITEM_PICKED_UP + ALL_SUFFIX);
		list.add(ITEM_USED + ALL_SUFFIX);
		return list;
	}

	private static List<String> getCustoms() {
		List<String> list = new ArrayList<>();
		for (var custom : BuiltInRegistries.CUSTOM_STAT) {
			String id = custom.getNamespace() + "." + custom.getPath();
			list.add(CUSTOM + id);
		}
		return list;
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
