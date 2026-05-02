package xyz.nekomilky.mcmod.statsscoreboard.configs;

import net.minecraft.network.chat.Component;
import xyz.nekomilky.mcmod.statsscoreboard.utils.Criteria;
import xyz.nekomilky.mcmod.statsscoreboard.utils.ExecutionResult;

public class ModConfig {
	private String defaultSidebarCriteria = "block.mined.all";
	private int configAutoSaveInterval = 60;
	private int refreshAllStatsDataInterval = 30;
	private boolean sidebarDisplayUUID = true;

	public String getDefaultSidebarCriteria() {
		return this.defaultSidebarCriteria;
	}

	public ExecutionResult<Component> setDefaultSidebarCriteria(String value) {
		if (!Criteria.isLegal(value)) {
			return new ExecutionResult<>(false, Component.translatable("statsscoreboard.command.fail.criteria_illegal"));
		}
		this.defaultSidebarCriteria = value;
		return new ExecutionResult<>(true, Component.translatable("statsscoreboard.command.success.set_default_sidebar", value));
	}

	public int getConfigAutoSaveInterval() {
		return this.configAutoSaveInterval;
	}

	public ExecutionResult<Component> setConfigAutoSaveInterval(int value) {
		if (value < 1) {
			return new ExecutionResult<>(false, Component.translatable("statsscoreboard.command.fail.value_illegal", "interval", value));
		}
		this.configAutoSaveInterval = value;
		return new ExecutionResult<>(true, Component.translatable("statsscoreboard.command.success.set_config_auto_save_interval", value));
	}

	public int getRefreshAllStatsDataInterval() {
		return this.refreshAllStatsDataInterval;
	}

	public ExecutionResult<Component> setRefreshAllStatsDataInterval(int value) {
		if (value < 1) {
			return new ExecutionResult<>(false, Component.translatable("statsscoreboard.command.fail.value_illegal", "interval", value));
		}
		this.refreshAllStatsDataInterval = value;
		return new ExecutionResult<>(true, Component.translatable("statsscoreboard.command.success.set_refresh_all_stats_data_interval", value));
	}

	public boolean getSidebarDisplayUUID() {
		return this.sidebarDisplayUUID;
	}

	public ExecutionResult<Component> setSidebarDisplayUUID(boolean value) {
		this.sidebarDisplayUUID = value;
		return new ExecutionResult<>(true, Component.translatable("statsscoreboard.command.success.set_sidebar_display_uuid", Boolean.toString(value)));
	}
}
