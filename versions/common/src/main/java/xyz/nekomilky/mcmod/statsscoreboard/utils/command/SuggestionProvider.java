package xyz.nekomilky.mcmod.statsscoreboard.utils.command;

import java.util.List;

public interface SuggestionProvider {
	List<String> getSuggestions(CommandContext context);
}
