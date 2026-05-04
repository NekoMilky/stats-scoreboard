package xyz.nekomilky.mcmod.statsscoreboard.commands;

import xyz.nekomilky.mcmod.statsscoreboard.utils.command.CommandArgumentNode;
import xyz.nekomilky.mcmod.statsscoreboard.utils.command.CommandLiteralNode;
import xyz.nekomilky.mcmod.statsscoreboard.utils.command.Commands;
import xyz.nekomilky.mcmod.statsscoreboard.utils.command.SuggestionProvider;

public class SSBCommands {
	private static SuggestionProvider criteriaSuggestionProvider = null;
	private static SuggestionProvider selectedCriteriaSuggestionProvider = null;

	public static void setCriteriaSuggestionProvider(SuggestionProvider provider) {
		criteriaSuggestionProvider = provider;
	}

	public static void setSelectedCriteriaSuggestionProvider(SuggestionProvider provider) {
		selectedCriteriaSuggestionProvider = provider;
	}

    public static void register() {
		About.init();
		CommandLiteralNode root = CommandLiteralNode.create("statsscoreboard")
			.execute(About::showAbout)
		    .then(CommandLiteralNode.create("about")
		        .execute(About::showAbout)
			)
		    .then(CommandLiteralNode.create("sidebar")
		        .execute(Sidebar::getSidebar)
		        .then(CommandLiteralNode.create("add")
		            .then(CommandArgumentNode.create("criteria", "String")
		                .suggest(criteriaSuggestionProvider)
		                .execute(Sidebar::addSidebar)
				    )
			    )
		        .then(CommandLiteralNode.create("remove")
		            .then(CommandArgumentNode.create("criteria", "String")
						.suggest(selectedCriteriaSuggestionProvider)
		                .execute(Sidebar::removeSidebar)
				    )
			    )
            )
		    .then(CommandLiteralNode.create("sidebarRotationInterval")
		        .execute(SidebarRotationInterval::getSidebarRotationInterval)
		        .then(CommandArgumentNode.create("interval", "Integer")
				    .execute(SidebarRotationInterval::setSidebarRotationInterval)
				)
			)
		    .then(CommandLiteralNode.create("defaultSidebar")
		        .requireOp()
		        .execute(DefaultSidebar::getDefaultSidebar)
		        .then(CommandArgumentNode.create("criteria", "String")
		            .suggest(criteriaSuggestionProvider)
		            .execute(DefaultSidebar::setDefaultSidebar)
				)
			)
		    .then(CommandLiteralNode.create("configAutoSaveInterval")
		        .requireOp()
		        .execute(ConfigAutoSaveInterval::getConfigAutoSaveInterval)
		        .then(CommandArgumentNode.create("interval", "Integer")
		            .execute(ConfigAutoSaveInterval::setConfigAutoSaveInterval)
				)
			)
		    .then(CommandLiteralNode.create("refreshAllStatsDataInterval")
		        .requireOp()
		        .execute(RefreshAllStatsDataInterval::getRefreshAllStatsDataInterval)
		        .then(CommandArgumentNode.create("interval", "Integer")
		            .execute(RefreshAllStatsDataInterval::setRefreshAllStatsDataInterval)
				)
			)
		    .then(CommandLiteralNode.create("sidebarDisplayUUID")
		        .requireOp()
		        .execute(SidebarDisplayUUID::getSidebarDisplayUUID)
		        .then(CommandArgumentNode.create("enabled", "Bool")
		            .execute(SidebarDisplayUUID::setSidebarDisplayUUID)
				)
			);
		Commands.register(root, "ssb");
    }
}
