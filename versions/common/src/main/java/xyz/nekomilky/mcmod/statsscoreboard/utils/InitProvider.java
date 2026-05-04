package xyz.nekomilky.mcmod.statsscoreboard.utils;

public interface InitProvider {
	void setCommandProvider();

	void setComponentProvider();

	void setCriteriaProvider();

	void setCriteriaSuggestionProvider();

	void setPlayerContextProvider();

	void setPlayerEventsProvider();

	void setSelectedCriteriaSuggestionProvider();

	void setServerEventsProvider();

	void setServerContextProvider();

	void setSidebarProvider();

	void setStatsPathProvider();
}
