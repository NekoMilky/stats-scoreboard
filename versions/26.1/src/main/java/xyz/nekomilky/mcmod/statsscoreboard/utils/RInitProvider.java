package xyz.nekomilky.mcmod.statsscoreboard.utils;

import xyz.nekomilky.mcmod.statsscoreboard.commands.SSBCommands;
import xyz.nekomilky.mcmod.statsscoreboard.events.PlayerEvents;
import xyz.nekomilky.mcmod.statsscoreboard.utils.event.RPlayerEventsProvider;
import xyz.nekomilky.mcmod.statsscoreboard.utils.event.RServerEventsProvider;
import xyz.nekomilky.mcmod.statsscoreboard.events.ServerEvents;
import xyz.nekomilky.mcmod.statsscoreboard.utils.command.RCommandsProvider;
import xyz.nekomilky.mcmod.statsscoreboard.utils.command.suggestion.RCriteriaSuggestionProvider;
import xyz.nekomilky.mcmod.statsscoreboard.utils.command.suggestion.RSelectedCriteriaSuggestionProvider;
import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.Component;
import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.RComponentProvider;
import xyz.nekomilky.mcmod.statsscoreboard.utils.command.Commands;
import xyz.nekomilky.mcmod.statsscoreboard.utils.criteria.Criteria;
import xyz.nekomilky.mcmod.statsscoreboard.utils.criteria.RCriteriaProvider;
import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Player;
import xyz.nekomilky.mcmod.statsscoreboard.utils.server.RPlayerContextProvider;
import xyz.nekomilky.mcmod.statsscoreboard.utils.server.RServerContextProvider;
import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Server;
import xyz.nekomilky.mcmod.statsscoreboard.utils.sidebar.RSidebarProvider;
import xyz.nekomilky.mcmod.statsscoreboard.utils.sidebar.Sidebar;
import xyz.nekomilky.mcmod.statsscoreboard.utils.stat.RStatsPathProvider;
import xyz.nekomilky.mcmod.statsscoreboard.utils.stat.StatsReader;

public class RInitProvider implements InitProvider {
	@Override
	public void setCommandProvider() {
		Commands.setCommandProvider(new RCommandsProvider());
	}

	@Override
	public void setComponentProvider() {
		Component.setComponentProvider(new RComponentProvider());
	}

	@Override
	public void setCriteriaProvider() {
		Criteria.setCriteriaProvider(new RCriteriaProvider());
	}

	@Override
	public void setCriteriaSuggestionProvider() {
		SSBCommands.setCriteriaSuggestionProvider(new RCriteriaSuggestionProvider());
	}

	@Override
	public void setPlayerContextProvider() {
		Player.setPlayerContextProvider(new RPlayerContextProvider());
	}

	@Override
	public void setPlayerEventsProvider() {
		PlayerEvents.setPlayerEventsProvider(new RPlayerEventsProvider());
	}

	@Override
	public void setSelectedCriteriaSuggestionProvider() {
		SSBCommands.setSelectedCriteriaSuggestionProvider(new RSelectedCriteriaSuggestionProvider());
	}

	@Override
	public void setServerEventsProvider() {
		ServerEvents.setServerEventsProvider(new RServerEventsProvider());
	}

	@Override
	public void setServerContextProvider() {
		Server.setServerContextProvider(new RServerContextProvider());
	}

	@Override
	public void setSidebarProvider() {
		Sidebar.setSidebarProvider(new RSidebarProvider());
	}

	@Override
	public void setStatsPathProvider() {
		StatsReader.setStatsPathProvider(new RStatsPathProvider());
	}
}
