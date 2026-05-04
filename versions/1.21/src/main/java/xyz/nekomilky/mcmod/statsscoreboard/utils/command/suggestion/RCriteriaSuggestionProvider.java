package xyz.nekomilky.mcmod.statsscoreboard.utils.command.suggestion;

import xyz.nekomilky.mcmod.statsscoreboard.utils.command.CommandContext;
import xyz.nekomilky.mcmod.statsscoreboard.utils.command.SuggestionProvider;
import xyz.nekomilky.mcmod.statsscoreboard.utils.criteria.Criteria;

import java.util.List;

public class RCriteriaSuggestionProvider implements SuggestionProvider {
	@Override
	public List<String> getSuggestions(CommandContext context) {
		return Criteria.get();
	}
}
