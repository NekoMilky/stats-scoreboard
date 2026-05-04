package xyz.nekomilky.mcmod.statsscoreboard.utils.command.suggestion;

import xyz.nekomilky.mcmod.statsscoreboard.configs.SSBConfigManager;
import xyz.nekomilky.mcmod.statsscoreboard.utils.command.CommandContext;
import xyz.nekomilky.mcmod.statsscoreboard.utils.command.SuggestionProvider;
import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Player;

import java.util.ArrayList;
import java.util.List;

public class RSelectedCriteriaSuggestionProvider implements SuggestionProvider {
	@Override
	public List<String> getSuggestions(CommandContext context) {
		var entity = context.getSource().getEntity();
		if (entity instanceof Player player) {
			return SSBConfigManager.playersConfig.getSelectedCriteria(player);
		}
		return new ArrayList<>();
	}
}
