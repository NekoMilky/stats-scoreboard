package xyz.nekomilky.mcmod.statsscoreboard;

import xyz.nekomilky.mcmod.statsscoreboard.utils.RInitProvider;
import xyz.nekomilky.mcmod.statsscoreboard.utils.SSBUtils;

public class Entry extends StatsScoreboard {
	@Override
	public void onInitialize() {
		SSBUtils.setInitProvider(new RInitProvider());
		super.onInitialize();
	}
}
