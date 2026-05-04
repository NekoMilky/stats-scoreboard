package xyz.nekomilky.mcmod.statsscoreboard.utils;

public class SSBUtils {
	private static InitProvider initProvider = null;

	public static void setInitProvider(InitProvider provider) {
		initProvider = provider;
	}

	public static void init() {
		initProvider.setCommandProvider();
		initProvider.setComponentProvider();
		initProvider.setCriteriaProvider();
		initProvider.setCriteriaSuggestionProvider();
		initProvider.setPlayerContextProvider();
		initProvider.setPlayerEventsProvider();
		initProvider.setSelectedCriteriaSuggestionProvider();
		initProvider.setServerContextProvider();
		initProvider.setServerEventsProvider();
		initProvider.setSidebarProvider();
		initProvider.setStatsPathProvider();
	}
}
