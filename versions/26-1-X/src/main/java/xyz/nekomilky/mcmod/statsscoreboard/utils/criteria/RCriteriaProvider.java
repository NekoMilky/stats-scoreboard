package xyz.nekomilky.mcmod.statsscoreboard.utils.criteria;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

public class RCriteriaProvider implements CriteriaProvider {
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

	@Override
	public List<String> getBlocks() {
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

	@Override
	public List<String> getEntities() {
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

	@Override
	public List<String> getItems() {
		List<String> list = new ArrayList<>();
		for (var item : BuiltInRegistries.ITEM) {
			var key = BuiltInRegistries.ITEM.getKey(item);
			String id = key.getNamespace() + "." + key.getPath();
			list.add(ITEM_CRAFTED + id);
			list.add(ITEM_DROPPED + id);
			list.add(ITEM_PICKED_UP + id);
			if (!isBlock(id)) {
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

	@Override
	public List<String> getCustoms() {
		List<String> list = new ArrayList<>();
		for (var custom : BuiltInRegistries.CUSTOM_STAT) {
			String id = custom.getNamespace() + "." + custom.getPath();
			list.add(CUSTOM + id);
		}
		return list;
	}

	@Override
	public boolean isBlock(String id) {
		var identifier = id.contains(".")
			? Identifier.tryBySeparator(id, '.')
			: Identifier.tryParse(id);
		return identifier != null && BuiltInRegistries.BLOCK.get(identifier).isPresent();
	}
}
