package xyz.nekomilky.mcmod.statsscoreboard.utils.criteria;

import java.util.List;

public interface CriteriaProvider {
	List<String> getBlocks();

	List<String> getCustoms();

	List<String> getEntities();

	List<String> getItems();

	boolean isBlock(String id);
}
