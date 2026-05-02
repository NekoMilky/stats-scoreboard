package xyz.nekomilky.mcmod.statsscoreboard.commands;

import com.mojang.brigadier.arguments.*;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import xyz.nekomilky.mcmod.statsscoreboard.commands.suggestions.CriteriaSuggestionProvider;
import xyz.nekomilky.mcmod.statsscoreboard.commands.suggestions.SelectedCriteriaSuggestionProvider;

public class SSBCommands {
	final private static CriteriaSuggestionProvider criteriaSuggestionProvider = new CriteriaSuggestionProvider();
	final private static SelectedCriteriaSuggestionProvider selectedCriteriaSuggestionProvider = new SelectedCriteriaSuggestionProvider();

    public static void register() {
		About.init();
        CommandRegistrationCallback.EVENT.register((dispatcher, buildContext, selection) -> {
            final var root = dispatcher.register(Commands.literal("statsscoreboard")
                .executes(About::showAbout)
				.then(Commands.literal("about")
					.executes(About::showAbout)
				)
				.then(Commands.literal("sidebar")
					.executes(Sidebar::getSidebar)
					.then(Commands.literal("add")
					    .then(Commands.argument("criteria", StringArgumentType.string())
					        .suggests(criteriaSuggestionProvider)
					        .executes(Sidebar::addSidebar)
						)
					)
					.then(Commands.literal("remove")
					    .then(Commands.argument("criteria", StringArgumentType.string())
					        .suggests(selectedCriteriaSuggestionProvider)
					        .executes(Sidebar::removeSidebar)
						)
					)
                )
				.then(Commands.literal("sidebarRotationInterval")
					.executes(SidebarRotationInterval::getSidebarRotationInterval)
					.then(Commands.argument("interval", IntegerArgumentType.integer(1))
					    .executes(SidebarRotationInterval::setSidebarRotationInterval)
					)
				)
				.then(Commands.literal("defaultSidebar")
					.requires((source) -> Commands.LEVEL_ADMINS.check(source.permissions()))
					.executes(DefaultSidebar::getDefaultSidebar)
					.then(Commands.argument("criteria", StringArgumentType.string())
						.suggests(criteriaSuggestionProvider)
						.executes(DefaultSidebar::setDefaultSidebar)
					)
				)
				.then(Commands.literal("configAutoSaveInterval")
					.requires((source) -> Commands.LEVEL_ADMINS.check(source.permissions()))
					.executes(ConfigAutoSaveInterval::getConfigAutoSaveInterval)
					.then(Commands.argument("interval", IntegerArgumentType.integer(1))
						.executes(ConfigAutoSaveInterval::setConfigAutoSaveInterval)
					)
				)
				.then(Commands.literal("refreshAllStatsDataInterval")
					.requires((source) -> Commands.LEVEL_ADMINS.check(source.permissions()))
					.executes(RefreshAllStatsDataInterval::getRefreshAllStatsDataInterval)
					.then(Commands.argument("interval", IntegerArgumentType.integer(1))
					    .executes(RefreshAllStatsDataInterval::setRefreshAllStatsDataInterval)
					)
				)
				.then(Commands.literal("sidebarDisplayUUID")
					.requires((source) -> Commands.LEVEL_ADMINS.check(source.permissions()))
					.executes(SidebarDisplayUUID::getSidebarDisplayUUID)
					.then(Commands.argument("enabled", BoolArgumentType.bool())
						.executes(SidebarDisplayUUID::setSidebarDisplayUUID)
					)
				)
            );
            // redirect /ssb
            dispatcher.register(Commands.literal("ssb")
				.executes(About::showAbout)
				.redirect(root)
			);
        });
    }
}
